package com.topherjn.batchjobs.visitor;

import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;
import java.io.IOException;

/**
 * A concrete Visitor that ONLY processes ReviewAuditor jobs.
 * This class encapsulates the "search for subset" (Concept 3)
 * and "process subset" (Concept 4) logic.
 */
public class ReviewJobRunnerVisitor implements JobVisitor {

    /**
     * This visitor's logic for a SalesReporter.
     * We are only interested in review jobs, so we do nothing.
     */
    @Override
    public void visit(SalesReporter job) {
        // This is part of the "search": we found a SalesReporter, so we skip it.
        System.out.println("Visitor is skipping: " + job.getClass().getSimpleName());
    }

    /**
     * This visitor's logic for a ReviewAuditor.
     * This is the job we are looking for, so we execute its 'process' method.
     */
    @Override
    public void visit(ReviewAuditor job) {
        // This is part of the "search": we found a ReviewAuditor, so we run it.
        System.out.println("Visitor is processing: " + job.getInputFile().getName());
        try {
            // This is the "process subset" part
            job.process();
            System.out.println(" > Visitor job success.");
        } catch (IOException e) {
            System.err.println(" > Visitor FAILED: " + e.getMessage());
        }
    }
}