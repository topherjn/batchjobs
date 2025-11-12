package com.topherjn.batchjobs;

// Import all required classes from the new subpackages
import com.topherjn.batchjobs.jobs.DataProcessor;
import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;
import com.topherjn.batchjobs.visitor.JobVisitor;
import com.topherjn.batchjobs.visitor.ReviewJobRunnerVisitor;

import java.io.IOException;
// We no longer need java.util.Arrays

/**
 * The main "mechanism" class, which contains the 'main' method.
 * It is modified to remove the 'findReviewJobs' (instanceof) method
 * and replace it with a polymorphic 'processJobsWithVisitor' method.
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
     * This method is unchanged. It runs ALL jobs.
     */
    public void runAllJobs() {
        System.out.println("--- Starting E-commerce Batch Service (All Jobs) ---");
        for (int i = 0; i < jobCount; i++) {
            DataProcessor job = jobs[i];
            System.out.println("Running job: " + job.getClass().getSimpleName() +
                    " on " + job.getInputFile().getName());
            try {
                job.process();
                System.out.println(" > Success. Output: " + job.getOutputFile().getName());
            } catch (IOException e) {
                System.err.println(" > FAILED: " + e.getMessage());
            }
        }
        System.out.println("\n--- Batch Service (All Jobs) Complete ---");
    }

    /**
     * REMOVED: The 'findReviewJobs()' method (which used 'instanceof') is gone.
     *
     * NEW: This method is fully polymorphic and accepts a 'strategy'
     * (the visitor) to apply to all jobs.
     */
    public void processJobsWithVisitor(JobVisitor visitor) {
        System.out.println("\n--- Processing Jobs with Visitor: " + visitor.getClass().getSimpleName() + " ---");
        for (int i = 0; i < jobCount; i++) {
            // We tell the job to 'accept' the visitor.
            // The job and visitor handle the rest via double dispatch.
            // This loop doesn't know what the visitor does.
            jobs[i].accept(visitor);
        }
    }

    /**
     * Main method, updated to use the new Visitor pattern.
     */
    public static void main(String[] args) {
        BatchService service = new BatchService();

        // Add jobs to the polymorphic array (Unchanged)
        service.addJob(new SalesReporter("sales.csv", "revenue_report.txt"));
        service.addJob(new ReviewAuditor("product_reviews.txt", "flagged_reviews.txt"));
        service.addJob(new ReviewAuditor("service_reviews.txt", "flagged_service_reviews.txt"));

        // Step 1: Run all jobs normally (Concept 1 & 2)
        service.runAllJobs();

        // --- Demonstrate Concept 3 & 4 (with Visitor) ---

        // Step 2: Create our "search and process" strategy (the Visitor).
        // This object IS the "search for subset" logic.
        JobVisitor reviewRunner = new ReviewJobRunnerVisitor();

        // Step 3: Pass this strategy to the service.
        // This single call fulfills both "search" and "process subset"
        // without using 'instanceof'.
        service.processJobsWithVisitor(reviewRunner);
    }
}