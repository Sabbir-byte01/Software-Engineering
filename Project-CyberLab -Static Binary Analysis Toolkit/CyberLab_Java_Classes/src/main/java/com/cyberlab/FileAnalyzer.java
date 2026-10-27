package com.cyberlab;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class FileAnalyzer {

    public String detectFormat(BinaryFile file) throws IOException {
        byte[] data = file.readAllBytes();

        if (data.length >= 4
                && data[0] == 0x7F
                && data[1] == 'E'
                && data[2] == 'L'
                && data[3] == 'F') {
            return "ELF";
        }

        if (data.length >= 2 && data[0] == 'M' && data[1] == 'Z') {
            return "PE/DOS executable candidate";
        }

        return "Unknown";
    }

    public String calculateSha256(BinaryFile file)
            throws IOException, NoSuchAlgorithmException {
        byte[] data = file.readAllBytes();
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(data);
        return HexFormat.of().formatHex(digest);
    }

    public String getSummary(BinaryFile file)
            throws IOException, NoSuchAlgorithmException {
        return "File: " + file.getFileName()
                + "\nSize: " + file.getSize() + " bytes"
                + "\nFormat: " + detectFormat(file)
                + "\nSHA-256: " + calculateSha256(file);
    }
}
