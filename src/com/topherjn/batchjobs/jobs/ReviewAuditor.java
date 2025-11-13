package com.topherjn.batchjobs.jobs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ReviewAuditor is a concrete DataProcessor job that reads a file of reviews in a standard format, detects low reviews
 * and writes those reviews to a flagged reviews file. Concept 1 - Inheritance */
public class ReviewAuditor extends DataProcessor {

    /** A ReviewAuditor IS-A DataProcessor, so it needs the names of two files: one for reading, one for writing*/
    public ReviewAuditor(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    /** A ReviewAuditor 'processes' a review file by opening it for reading, detecting low reviews ('[1-STAR]'),
     *  and writing them to a flagged reviews file-- which is different from other DataProcessors.
     *  Concept 1 - Polymorphism */
    @Override
    public void process() throws IOException {

        // Keep track of number of low reviews for reporting
        int flaggedCount = 0;

        // Using a BufferReader reader saves data in memory before processing in the algorithm and before writing to
        // disk, minimizing expensive file I/O operations.  Concepts 2, 3, 4.
        // Uses try-with-resources to minimize unclosed files (another optimization)
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {

            // Reviews files are organized by one line == one review
            String line;
            while ((line = reader.readLine()) != null) {

                // "Bad" review also begin with the same pattern
                // Detected bad reviews are written to the output file
                if (line.startsWith("[1-STAR]")) {
                    writer.write(line);
                    writer.newLine();
                    flaggedCount++;
                }
            }
        }
        // Report the number of reviews flagged
        System.out.println(" > Flagged " + flaggedCount + " negative review(s).");
    }
}