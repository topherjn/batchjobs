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
}