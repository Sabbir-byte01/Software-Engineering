package com.cyberlab;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class CyberLabDemo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java com.cyberlab.CyberLabDemo <path-to-file>");
            return;
        }

        try {
            BinaryFile file = new BinaryFile(args[0]);

            if (!file.exists()) {
                System.err.println("Error: file not found or not a regular file.");
                return;
            }

            AnalysisReport report = AnalysisReport.create(file);
            System.out.println(report.toText());

            Path output = Path.of("analysis-report.txt");
            Files.writeString(output, report.toText(), StandardCharsets.UTF_8);
            System.out.println("\nReport saved to: " + output.toAbsolutePath());
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Analysis failed: " + e.getMessage());
        }
    }
}
