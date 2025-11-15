package com.topherjn.batchjobs.jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A concrete job that generates a total revenue report from a sales CSV.
 */
public class SalesReporter extends DataProcessor {

    /**
     * Creates a new sales reporter job.
     *
     * @param inputFilename  The input sales CSV.
     * @param outputFilename The output report file.
     */
    public SalesReporter(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    /**
     * Reads the sales CSV, calculates total revenue, and writes the report.
     *
     * @throws IOException if a file I/O error occurs.
     */
    @Override
    public void process() throws IOException {
        double totalRevenue = 0.0;

        // Use try-with-resources for optimized, safe file handling
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()))) {
            String line;
            // Skip header line
            reader.readLine(); 
            
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    // OrderID,ProductID,Quantity,PricePerItem
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    totalRevenue += (quantity * price);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println(" > Skipping malformed sales line: " + line);
                }
            }
        }

        // Use try-with-resources for optimized, safe file writing
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {
            // Format to 2 decimal places for currency
            writer.write(String.format("TotalRevenue: %.2f", totalRevenue));
        }
    }
}
