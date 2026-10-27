package com.cyberlab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ImportAnalyzer {
    private static final String[] KNOWN_FUNCTIONS = {
        "printf", "puts", "scanf", "fopen", "fread", "fwrite",
        "strcmp", "strncmp", "memcmp", "malloc", "free",
        "CreateFileA", "CreateFileW", "ReadFile", "WriteFile",
        "VirtualAlloc", "VirtualProtect", "LoadLibraryA",
        "GetProcAddress", "WinExec", "ShellExecuteA",
        "ptrace", "execve", "open", "read", "write", "system"
    };

    /*
     * Beginner-level heuristic only: searches known function names in the raw bytes.
     * This is NOT a proper PE/ELF import-table parser and may miss or falsely match names.
     */
    public List<String> findPossibleFunctionNames(BinaryFile file) throws IOException {
        String raw = new String(file.readAllBytes(), StandardCharsets.ISO_8859_1);
        Set<String> matches = new LinkedHashSet<>();

        for (String function : KNOWN_FUNCTIONS) {
            if (raw.contains(function)) {
                matches.add(function);
            }
        }

        return new ArrayList<>(matches);
    }
}
