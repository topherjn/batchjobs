package com.topherjn.batchjobs.jobs;

import com.topherjn.batchjobs.visitor.JobVisitor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Concrete job for auditing reviews.
 * Modified to implement the 'accept' method.
 */
public class ReviewAuditor extends DataProcessor {

    public ReviewAuditor(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    @Override
    public void process() throws IOException {
        int flaggedCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[1-STAR]")) {
                    writer.write(line);
                    writer.newLine();
                    flaggedCount++;
                }
            }
        }
        System.out.println(" > Flagged " + flaggedCount + " negative review(s).");
    }

    /**
     * Implements the 'accept' method.
     * This calls the 'visit' method on the visitor, passing *this* object.
     * The JVM will select the correct visit(ReviewAuditor) method overload.
     */
    @Override
    public void accept(JobVisitor visitor) {
        visitor.visit(this);
    }
}