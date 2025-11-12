package com.topherjn.batchjobs.jobs;

import com.topherjn.batchjobs.visitor.JobVisitor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Concrete job for sales reporting.
 * Modified to implement the 'accept' method.
 */
public class SalesReporter extends DataProcessor {

    public SalesReporter(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    @Override
    public void process() throws IOException {
        double totalRevenue = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    totalRevenue += (quantity * price);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println(" > Skipping malformed sales line: " + line);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {
            writer.write(String.format("TotalRevenue: %.2f", totalRevenue));
        }
    }

    /**
     * Implements the 'accept' method.
     * This calls the 'visit' method on the visitor, passing *this* object.
     * This is the core of "double dispatch," as the JVM selects
     * the correct visit(SalesReporter) method overload.
     */
    @Override
    public void accept(JobVisitor visitor) {
        visitor.visit(this);
    }
}