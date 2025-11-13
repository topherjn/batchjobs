package com.topherjn.batchjobs.jobs;

import java.io.*;

public class SalesReporter extends DataProcessor {

    /** A SalesReader DataProcessor reads a sales file, totals revenue, and writes that total to a revenue report. */
    public SalesReporter(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    /** A SalesReporter 'processes' a sales csv file by opening it for reading, totaling up the revenue data contained
     *  therein, and writing a total revenue report to a file-- which is different from other DataProcessors.
     *  Concept 1 - Polymorphism */
    @Override
    public void process() throws IOException {

        // initialize revenue total
        double totalRevenue = 0.0;

        // Using a BufferReader reader saves data in memory before processing in the algorithm and before writing to
        // disk, minimizing expensive file I/O operations.  Concepts 2, 3, 4.
        // Uses try-with-resources to minimize unclosed files (another optimization)
        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()))) {
            String line;

            reader.readLine();

            // one sale per line in csv format, so
            while((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");

                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    totalRevenue += price * quantity;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping malformed sales line: " + line);
                }
            }
        }

        // Just one line is written per sales report, so writing only after loop termination
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {
            writer.write(String.format("Total Revenue: %.2f", totalRevenue));
        }
    }
}