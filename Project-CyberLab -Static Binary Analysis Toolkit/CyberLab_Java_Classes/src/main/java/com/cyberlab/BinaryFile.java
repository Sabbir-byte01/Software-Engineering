package com.cyberlab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BinaryFile {
    private final Path path;

    public BinaryFile(String filePath) {
        this.path = Paths.get(filePath).toAbsolutePath().normalize();
    }

    public Path getPath() {
        return path;
    }

    public String getFileName() {
        return path.getFileName().toString();
    }

    public long getSize() throws IOException {
        return Files.size(path);
    }

    public boolean exists() {
        return Files.exists(path) && Files.isRegularFile(path);
    }

    public byte[] readAllBytes() throws IOException {
        if (!exists()) {
            throw new IOException("File does not exist or is not a regular file.");
        }
        return Files.readAllBytes(path);
    }
}
