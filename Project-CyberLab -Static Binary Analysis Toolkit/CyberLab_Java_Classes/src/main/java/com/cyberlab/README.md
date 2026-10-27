# CyberLab — Java Static Binary Analysis Classes

This starter version demonstrates five core classes for a beginner static-analysis project:

1. `BinaryFile`
2. `FileAnalyzer`
3. `StringExtractor`
4. `ImportAnalyzer`
5. `AnalysisReport`

`CyberLabDemo` is a small command-line runner so the classes can be tested before building the JavaFX interface.

## Requirements

- JDK 17 or newer (the code uses `HexFormat` and `Files.writeString`)
- No third-party dependencies

## Compile

From the folder containing `src`:

```bash
javac -d out src/main/java/com/cyberlab/*.java
```


Windows PowerShell:

```powershell
java -cp out com.cyberlab.CyberLabDemo "C:\path\to\sample.exe"
```

The demo prints a report and writes `analysis-report.txt` in the current working directory.

## Important limitations

- `FileAnalyzer` identifies ELF magic and the `MZ` signature only; `MZ` does not by itself prove that a file is a valid PE executable.
- `ImportAnalyzer` searches for known function names in raw bytes. It is a teaching heuristic, not a correct PE/ELF import-table parser.
- Analyze only files you are authorized to inspect. This program does not execute the selected binary.
- A JavaFX UI and MySQL/JDBC persistence can be added as separate layers later.
