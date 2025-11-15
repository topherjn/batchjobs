package com.topherjn.batchjobs;

import com.topherjn.batchjobs.jobs.DataProcessor;
import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;

public class BatchService {

    /** instance variables
     * the core element is an array of all kinds of data processors - the scenario is that these reports are run
     * periodically.  We keep track of the number of jobs because we're using a simple array (max size 10) to make the
     * demo simpler */
    private final DataProcessor[] jobs;
    private int jobCount;
    private static final int MAX_JOBS = 10;

    // constructor creates an array of DataProcessors and a variable
    // to keep track of how many jobs
    public BatchService() {
        this.jobs = new DataProcessor[MAX_JOBS];
        this.jobCount = 0;
    }

    // add jobs to the DataProcessor array
    public void addJob(DataProcessor job) {
        if (jobCount < MAX_JOBS) {
            jobs[jobCount] = job;
            jobCount++;
        } else {
            System.err.println("Job queue is full. Cannot add " + job.getClass().getSimpleName());
        }
    }

    public void runAllJobs() {

        // Signal batch start


        // iterate over jobs in the DataProcessor array (named "jobs")


        // Signal batch end

    }

    /** search the array for certain kinds (subclasses) of jobs
     * Concept 3 */
    public DataProcessor[] findReviewJobs() {

        // Signal start of search

        // create the array at a size to fit all jobs
        // in order to accommodate all ReviewAudit jobs


        // initialize a review count variable


        // using instanceof, find only ReviewAuditor jobs
        // and add them to the subset array


        // Report how many ReviewAuditor jobs found

        // return the subset of the DataProcessor array that contains only reviews jobs

        return new DataProcessor[0]; // Placeholder return
    }

    public static void main(String[] args) {

        // Instantiate the BatchService object
        // a BatchService object has a DataService array of max size 10
        // and runs all the DataService jobs
        // It then searches the subset of review jobs
        BatchService service = new BatchService();

        // add jobs to the DataService array
        service.addJob(new SalesReporter("sales.csv", "revenue_report.txt"));
        service.addJob(new ReviewAuditor("product_reviews.txt", "flagged_reviews.txt"));
        service.addJob(new ReviewAuditor("service_reviews.txt", "flagged_service_reviews.txt"));

        // run all the jobs


        // Search for the subset (Concept 3)


        // Write the subset to a file (Concept 4)

    }
}