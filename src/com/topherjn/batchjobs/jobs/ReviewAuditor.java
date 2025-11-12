package com.topherjn.batchjobs.jobs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A concrete job that reads a customer review file, searches for
 * lines containing "[1-STAR]", and writes only those lines to an output file.
 */
public class ReviewAuditor extends DataProcessor {

    public ReviewAuditor(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    /**
     * Overridden implementation of the 'process' method.
     * This logic is specific to filtering (searching) a text file.
     * Uses BufferedReader/BufferedWriter for performance.
     */
    @Override
    public void process() throws IOException {
        int flaggedCount = 0;

        // Use try-with-resources for efficient I/O
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // This is the "search" part of the job
                if (line.startsWith("[1-STAR]")) {
                    writer.write(line);
                    writer.newLine();
                    flaggedCount++;
                }
            }
        }
        System.out.println(" > Flagged " + flaggedCount + " negative review(s).");
    }
}