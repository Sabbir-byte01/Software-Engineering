package com.cyberlab;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

public class AnalysisReport {
    private final BinaryFile binaryFile;
    private final String format;
    private final String sha256;
    private final List<String> strings;
    private final List<String> possibleImports;
    private final LocalDateTime generatedAt;

    public AnalysisReport(BinaryFile binaryFile,
                          String format,
                          String sha256,
                          List<String> strings,
                          List<String> possibleImports) {
        this.binaryFile = binaryFile;
        this.format = format;
        this.sha256 = sha256;
        this.strings = List.copyOf(strings);
        this.possibleImports = List.copyOf(possibleImports);
        this.generatedAt = LocalDateTime.now();
    }

    public BinaryFile getBinaryFile() {
        return binaryFile;
    }

    public String getFormat() {
        return format;
    }

    public String getSha256() {
        return sha256;
    }

    public List<String> getStrings() {
        return strings;
    }

    public List<String> getPossibleImports() {
        return possibleImports;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public static AnalysisReport create(BinaryFile file) throws IOException, NoSuchAlgorithmException {
        FileAnalyzer analyzer = new FileAnalyzer();
        StringExtractor extractor = new StringExtractor(4);
        ImportAnalyzer importAnalyzer = new ImportAnalyzer();

        return new AnalysisReport(
                file,
                analyzer.detectFormat(file),
                analyzer.calculateSha256(file),
                extractor.extract(file),
                importAnalyzer.findPossibleFunctionNames(file)
        );
    }

    public String toText() throws IOException {
        StringBuilder report = new StringBuilder();
        report.append("CyberLab Static Analysis Report\n");
        report.append("Generated: ").append(generatedAt).append("\n");
        report.append("File: ").append(binaryFile.getFileName()).append("\n");
        report.append("Path: ").append(binaryFile.getPath()).append("\n");
        report.append("Size: ").append(binaryFile.getSize()).append(" bytes\n");
        report.append("Format: ").append(format).append("\n");
        report.append("SHA-256: ").append(sha256).append("\n\n");

        report.append("Possible imported function names (heuristic):\n");
        if (possibleImports.isEmpty()) {
            report.append("(No known names found)\n");
        } else {
            for (String name : possibleImports) {
                report.append("- ").append(name).append("\n");
            }
        }

        report.append("\nPrintable strings:\n");
        for (String value : strings) {
            report.append(value).append("\n");
        }

        return report.toString();
    }
}
