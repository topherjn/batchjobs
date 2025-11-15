package com.topherjn.batchjobs;

import com.topherjn.batchjobs.jobs.DataProcessor;
import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Orchestrates the execution of various batch jobs.
 * This class contains the main method for the demonstration.
 */
public class BatchService {

    // the core element is an array of all kinds of data processors
    private final DataProcessor[] jobs;
    // keep track of the number of jobs because we're using a simple array
    private int jobCount;
    private static final int MAX_JOBS = 10;

    /**
     * Initializes the batch service with a fixed-size job array.
     */
    public BatchService() {
        this.jobs = new DataProcessor[MAX_JOBS];
        this.jobCount = 0;
    }

    /**
     * Adds a new job to the execution queue.
     * If the queue is full, an error is printed.
     *
     * @param job The DataProcessor job to add.
     */
    public void addJob(DataProcessor job) {
        if (jobCount < MAX_JOBS) {
            jobs[jobCount] = job;
            jobCount++;
        } else {
            System.err.println("Job queue is full. Cannot add " + job.getClass().getSimpleName());
        }
    }

    /**
     * Runs all jobs currently in the queue.
     */
    public void runAllJobs() {
        // Left empty for live-coding
    }

    /**
     * Searches the queue for all ReviewAuditor jobs.
     *
     * @return A new, non-null array containing only the ReviewAuditor jobs.
     */
    public DataProcessor[] findReviewJobs() {
        // Left empty for live-coding
        return new DataProcessor[0]; // Placeholder return
    }

    /**
     * Main driver method for the demonstration.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        BatchService service = new BatchService();

        service.addJob(new SalesReporter("sales.csv", "revenue_report.txt"));
        service.addJob(new ReviewAuditor("product_reviews.txt", "flagged_reviews.txt"));
        service.addJob(new ReviewAuditor("service_reviews.txt", "flagged_service_reviews.txt"));

        // Live-coding will start here
    }
}
