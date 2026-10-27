package com.cyberlab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StringExtractor {
    private final int minimumLength;

    public StringExtractor() {
        this(4);
    }

    public StringExtractor(int minimumLength) {
        if (minimumLength < 1) {
            throw new IllegalArgumentException("Minimum length must be at least 1.");
        }
        this.minimumLength = minimumLength;
    }

    public List<String> extract(BinaryFile file) throws IOException {
        byte[] data = file.readAllBytes();
        List<String> strings = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (byte value : data) {
            int c = value & 0xFF;

            if (c >= 32 && c <= 126) {
                current.append((char) c);
            } else {
                addIfLongEnough(current, strings);
            }
        }

        addIfLongEnough(current, strings);
        return strings;
    }

    private void addIfLongEnough(StringBuilder current, List<String> output) {
        if (current.length() >= minimumLength) {
            output.add(current.toString());
        }
        current.setLength(0);
    }
}
