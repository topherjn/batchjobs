package com.topherjn.batchjobs;


import com.topherjn.batchjobs.jobs.DataProcessor;
import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;

import java.io.IOException;
import java.util.Arrays;

/** The batch service */
public class BatchService {

    // instance variables
    // the core element is an array of all kinds of data processors - the scenario is that these reports are run
    // periodically.  We keep track of the number of jobs because we're using a simple array (max size 10) to make the
    // demo simpler
    private final DataProcessor[] jobs;
    private int jobCount;
    private static final int MAX_JOBS = 10;

    // constructor creates an array of DataProcessors and a variable
    // to keep track of how many jobs
    public BatchService() {
        this.jobs = new DataProcessor[MAX_JOBS];
        this.jobCount = 0;
    }

    // add jobs to DataProcessor array.  The array has a static max size
    public void addJob(DataProcessor job) {
        if (jobCount < MAX_JOBS) {
            jobs[jobCount] = job;
            jobCount++;
        } else {
            System.err.println("Job queue is full. Cannot add " + job.getClass().getSimpleName());
        }
    }

    // iterate over all the DataProcessor jobs and do their tasks, depending on what kind of job
    // each is
    public void runAllJobs() {
        System.out.println("--- Starting E-commerce Batch Service ---");

        for (int i = 0; i < jobCount; i++) {
            DataProcessor job = jobs[i];
            System.out.println("Running job: " + job.getClass().getSimpleName() +
                    " on " + job.getInputFile().getName());
            try {
                // process() is polymorphic
                job.process();
                System.out.println(" > Success. Output: " + job.getOutputFile().getName());
            } catch (IOException e) {
                System.err.println(" > FAILED: " + e.getMessage());
            }
        }
        System.out.println("\n--- Batch Service Complete ---");
    }

    // search the array for certain kinds (subclasses) of jobs
    // Concept 3
    public DataProcessor[] findReviewJobs() {
        System.out.println("\n--- Searching for ReviewAuditor Jobs ---");
        DataProcessor[] reviewProcessors = new DataProcessor[jobCount];
        int reviewCount = 0;

        // using instanceof to make the polymorphism clear
        for (int i = 0; i < jobCount; i++) {
            if (jobs[i] instanceof ReviewAuditor) {
                reviewProcessors[reviewCount] = jobs[i];
                reviewCount++;
            }
        }
        System.out.println("Found " + reviewCount + " review audit jobs.");

        return Arrays.copyOf(reviewProcessors, reviewCount);
    }


    public static void main(String[] args) {

        // a BatchService object has a DataService array of max size 10
        BatchService service = new BatchService();

        // Add the new e-commerce jobs to the abstract array
        // calculate total revenue from sales
        // Detect low product reviews and report them in a separate file
        // Detect low service reviews and report them in a separate file
        service.addJob(new SalesReporter("sales.csv", "revenue_report.txt"));
        service.addJob(new ReviewAuditor("product_reviews.txt", "flagged_reviews.txt"));
        service.addJob(new ReviewAuditor("service_reviews.txt", "flagged_service_reviews.txt"));

        // Run all jobs
        service.runAllJobs();

        // --- Demonstrate Concepts 3 & 4 ---
        // 1. Search for the subset (Concept 3)
        DataProcessor[] reviewJobs = service.findReviewJobs();

        // Write the subset to a file
        System.out.println("\n--- Processing ONLY the review job subset ---");
        for (DataProcessor job : reviewJobs) {
            try {
                System.out.println("Running subset job: " + job.getInputFile().getName());
                job.process(); // This polymorphic call *is* the "write to file" step
            } catch (IOException e) {
                System.err.println(" > FAILED: " + e.getMessage());
            }
        }
    }
}