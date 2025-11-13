package com.topherjn.batchjobs.jobs;

import java.io.*;

public class SalesReporter extends DataProcessor {

    public SalesReporter(String inputFilename, String outputFilename) {
        super(inputFilename, outputFilename);
    }

    @Override
    public void process() throws IOException {

        double totalRevenue = 0.0;


        try (BufferedReader reader = new BufferedReader(new FileReader(getInputFile()))) {
            String line;

            reader.readLine();

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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutputFile()))) {
            writer.write(String.format("Total Revenue: %.2f", totalRevenue));
        }
    }
}