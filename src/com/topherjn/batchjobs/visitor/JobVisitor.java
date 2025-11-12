package com.topherjn.batchjobs.visitor;

import com.topherjn.batchjobs.jobs.ReviewAuditor;
import com.topherjn.batchjobs.jobs.SalesReporter;

/**
 * The Visitor interface.
 * It defines an overloaded 'visit' operation for each concrete job type.
 * This allows us to add new operations (like "run only review jobs")
 * without changing the job classes themselves.
 */
public interface JobVisitor {
    /**
     * The operation to perform on a SalesReporter object.
     * @param job The SalesReporter job to visit.
     */
    void visit(SalesReporter job);

    /**
     * The operation to perform on a ReviewAuditor object.
     * @param job The ReviewAuditor job to visit.
     */
    void visit(ReviewAuditor job);
}