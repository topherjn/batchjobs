package com.topherjn.batchjobs.jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A concrete job that filters a review file for negative (1-star) reviews.
 */
public class ReviewAuditor extends DataProcessor {

    /**
     * Creates a new review auditor job.
     *
     * @param inputFilename  The input review file.
     * @param outputFilename The output file for flagged reviews.
     */
    public ReviewAuditor(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    /**
     * Reads the review file, finds 1-star reviews, and writes them to the output.
     *
     * @throws IOException if a file I/O error occurs.
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
