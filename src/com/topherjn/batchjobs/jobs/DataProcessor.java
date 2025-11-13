package com.topherjn.batchjobs.jobs;

import java.io.File;
import java.io.IOException;

/** Base abstract class for batch processing data. Creates the contract for processing files with the abstract method
*   process.  */
public abstract class DataProcessor {

    /**  once the input and output files are determined for a job, they don't change.
    *   Private by Principle of Least Privilege (they could also be protected)  */
    private final File inputFile;
    private final File outputFile;

    /** All data files that are processed will have an input file and output file
    *   This object is only ever instantiated in subclasses, so make it protected, but not public */
    protected DataProcessor(String inputFilename, String outputFilename) {
        if (inputFilename == null || outputFilename == null) {
            throw new IllegalArgumentException("Filenames cannot be null");
        }
        this.inputFile = new File(inputFilename);
        this.outputFile = new File(outputFilename);
    }

    /** Abstract process method.  All DataProcessor jobs are processed in the batch, but that is done so in different
    *  ways.  All subclasses must implement this method */
    public abstract void process() throws IOException;

    /** Accessors to private instance variables.  */
    public File getInputFile() {
        return inputFile;
    }
    public File getOutputFile() {
        return outputFile;
    }
}
