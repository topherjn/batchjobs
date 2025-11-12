package com.topherjn.batchjobs.jobs;

import java.io.File;
import java.io.IOException;

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

    public abstract void process() throws IOException;

    public File getInputFile() {
        return inputFile;
    }
    public File getOutputFile() {
        return outputFile;
    }
}
