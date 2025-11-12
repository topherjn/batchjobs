package com.topherjn.batchjobs;


import com.topherjn.batchjobs.jobs.DataProcessor;
import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;

import java.io.IOException;
import java.util.Arrays; // For Arrays.copyOf

/**
 * The main "mechanism" class.
 * It holds a plain array of abstract DataProcessor jobs and runs them.
 */
public class BatchService {

    private DataProcessor[] jobs;
    private int jobCount;
    private static final int MAX_JOBS = 10;

    public BatchService() {
        this.jobs = new DataProcessor[MAX_JOBS];
        this.jobCount = 0;
    }

    public void addJob(DataProcessor job) {
        if (jobCount < MAX_JOBS) {
            jobs[jobCount] = job;
            jobCount++;
        } else {
            System.err.println("Job queue is full. Cannot add " + job.getClass().getSimpleName());
        }
    }

    /**
     * The core polymorphic mechanism.
     * Iterates up to 'jobCount' and calls process() on each job.
     */
    public void runAllJobs() {
        System.out.println("--- Starting E-commerce Batch Service ---");

        for (int i = 0; i < jobCount; i++) {
            DataProcessor job = jobs[i];
            System.out.println("Running job: " + job.getClass().getSimpleName() +
                    " on " + job.getInputFile().getName());
            try {
                // THE POLYMORPHIC CALL:
                job.process();
                System.out.println(" > Success. Output: " + job.getOutputFile().getName());
            } catch (IOException e) {
                System.err.println(" > FAILED: " + e.getMessage());
            }
        }
        System.out.println("\n--- Batch Service Complete ---");
    }

    /**
     * CONCEPT 3: Searching an array of objects for a subset.
     * Finds and returns a new array containing only the ReviewAuditor jobs.
     */
    public DataProcessor[] findReviewJobs() {
        System.out.println("\n--- Searching for ReviewAuditor Jobs ---");
        DataProcessor[] subset = new DataProcessor[jobCount];
        int subsetCount = 0;

        for (int i = 0; i < jobCount; i++) {
            if (jobs[i] instanceof ReviewAuditor) {
                subset[subsetCount] = jobs[i];
                subsetCount++;
            }
        }
        System.out.println("Found " + subsetCount + " review audit jobs.");
        return Arrays.copyOf(subset, subsetCount);
    }

    /**
     * Main method to set up and run the e-commerce demonstration.
     */
    public static void main(String[] args) {
        BatchService service = new BatchService();

        // Add the new e-commerce jobs to the abstract array
        service.addJob(new SalesReporter("sales.csv", "revenue_report.txt"));
        service.addJob(new ReviewAuditor("product_reviews.txt", "flagged_reviews.txt"));
        service.addJob(new ReviewAuditor("service_reviews.txt", "flagged_service_reviews.txt"));

        // Run all jobs
        service.runAllJobs();

        // --- Demonstrate Concept 3 & 4 ---
        // 1. Search for the subset (Concept 3)
        DataProcessor[] reviewJobs = service.findReviewJobs();

        // 2. Now, process ONLY that subset (Concept 4)
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