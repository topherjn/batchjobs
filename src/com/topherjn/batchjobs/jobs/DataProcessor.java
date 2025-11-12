package com.topherjn.batchjobs.jobs;

import com.topherjn.batchjobs.visitor.JobVisitor;
import java.io.File;
import java.io.IOException;

/**
 * The abstract base class for all processing jobs.
 * It is modified to include the 'accept' method for the Visitor pattern.
 */
public abstract class DataProcessor {

    private final File inputFile;
    private final File outputFile;

    protected DataProcessor(String inputFilename, String outputFilename) {
        if (inputFilename == null || outputFilename == null) {
            throw new IllegalArgumentException("Filenames cannot be null");
        }
        this.inputFile = new File(inputFilename);
        this.outputFile = new File(outputFilename);
    }

    /**
     * The job's core processing logic. (Unchanged)
     */
    public abstract void process() throws IOException;

    /**
     * NEW: The 'accept' method for the Visitor Pattern.
     * This forces all concrete subclasses to implement this method,
     * which enables the "double dispatch" mechanism.
     *
     * @param visitor The visitor that will operate on this job.
     */
    public abstract void accept(JobVisitor visitor);

    // --- Getters (Unchanged) ---
    public File getInputFile() {
        return inputFile;
    }
    public File getOutputFile() {
        return outputFile;
    }
}