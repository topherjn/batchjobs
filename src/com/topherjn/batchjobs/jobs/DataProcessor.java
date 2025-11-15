package com.topherjn.batchjobs.jobs;

import java.io.File;
import java.io.IOException;

/**
 * The abstract contract for all batch processing jobs.
 */
public abstract class DataProcessor {

    /* once the input and output files are determined for a job, they don't change.
    * Private by Principle of Least Privilege  */
    private final File inputFile;
    private final File outputFile;

    /**
     * Protected constructor for subclasses.
     *
     * @param inputFilename  The non-null name of the file to read.
     * @param outputFilename The non-null name of the file to write.
     * @throws IllegalArgumentException if filenames are null.
     */
    protected DataProcessor(String inputFilename, String outputFilename) {
        if (inputFilename == null || outputFilename == null) {
            throw new IllegalArgumentException("Filenames cannot be null");
        }
        this.inputFile = new File(inputFilename);
        this.outputFile = new File(outputFilename);
    }

    /**
     * The core processing logic unique to each job.
     * Subclasses must implement this method.
     *
     * @throws IOException if a file I/O error occurs.
     */
    public abstract void process() throws IOException;

    /**
     * Gets the input file for this job.
     * @return The input file.
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * Gets the output file for this job.
     * @return The output file.
     */
    public File getOutputFile() {
        return outputFile;
    }
}
