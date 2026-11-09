package com.sabbir.mathquiz.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Contains the calculation and multiple-choice generation rules. */
public final class QuizLogic {
    public static final long MAX_INTEGER = 1_000_000_000L;
    public static final double MAX_DEGREES = 1_000_000.0;

    private QuizLogic() {
    }

    public static String getTitle(int questionIndex) {
        return switch (questionIndex) {
            case 0 -> "Prime or Not Prime";
            case 1 -> "Remainder Challenge";
            case 2 -> "Sine Challenge";
            case 3 -> "Cosine Challenge";
            default -> "Quiz Complete";
        };
    }

    public static String getDescription(int questionIndex) {
        return switch (questionIndex) {
            case 0 -> "Enter a whole number and decide whether it is Prime or Not Prime.";
            case 1 -> "Enter a non-negative whole number. Find its remainder when divided by 3.";
            case 2 -> "Enter an angle in degrees and choose its sine value.";
            case 3 -> "Enter an angle in degrees and choose its cosine value.";
            default -> "You have completed all four questions.";
        };
    }

    public static String getInputLabel(int questionIndex) {
        return questionIndex < 2 ? "Enter a whole number" : "Enter an angle in degrees";
    }

    public static String getInputStep(int questionIndex) {
        return questionIndex < 2 ? "1" : "any";
    }

    public static String getInputMin(int questionIndex) {
        return questionIndex < 2 ? "0" : "-1000000";
    }

    public static String getInputMax(int questionIndex) {
        return questionIndex < 2 ? String.valueOf(MAX_INTEGER) : "1000000";
    }

    /** Validates the user's input and returns the correct answer plus 2 distractors. */
    public static AnswerSet createAnswerSet(int questionIndex, String rawInput) {
        if (rawInput == null || rawInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a value first.");
        }

        String input = rawInput.trim();
        String correct;
        List<String> options = new ArrayList<>();

        switch (questionIndex) {
            case 0 -> {
                long number = parseInteger(input);
                correct = isPrime(number) ? "Prime" : "Not Prime";
                options.add("Prime");
                options.add("Not Prime");
            }
            case 1 -> {
                long number = parseInteger(input);
                long remainder = number % 3;
                correct = String.valueOf(remainder);
                options.add(correct);
                options.add(String.valueOf(remainder + 3));
                options.add(String.valueOf(remainder - 2));
            }
            case 2, 3 -> {
                double degrees = parseDegrees(input);
                double radians = Math.toRadians(degrees);
                double value = questionIndex == 2 ? Math.sin(radians) : Math.cos(radians);
                correct = format3(value);
                options.add(correct);
                options.add(format3(new BigDecimal(correct).add(BigDecimal.valueOf(3)).doubleValue()));
                options.add(format3(new BigDecimal(correct).subtract(BigDecimal.valueOf(2)).doubleValue()));
            }
            default -> throw new IllegalArgumentException("There are no more questions.");
        }

        Collections.shuffle(options);
        return new AnswerSet(input, correct, options);
    }

    private static long parseInteger(String input) {
        try {
            long value = Long.parseLong(input);
            if (value < 0 || value > MAX_INTEGER) {
                throw new IllegalArgumentException("Enter a whole number from 0 to 1,000,000,000.");
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid whole number (digits only).");
        }
    }

    private static double parseDegrees(String input) {
        try {
            double value = Double.parseDouble(input);
            if (!Double.isFinite(value) || Math.abs(value) > MAX_DEGREES) {
                throw new IllegalArgumentException("Enter a degree value from -1,000,000 to 1,000,000.");
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid degree value.");
        }
    }

    private static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (long divisor = 3; divisor <= number / divisor; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private static String format3(double value) {
        return BigDecimal.valueOf(value)
                .setScale(3, RoundingMode.HALF_UP)
                .toPlainString();
    }

    public record AnswerSet(String input, String correctAnswer, List<String> options) {
        public AnswerSet {
            options = List.copyOf(options);
        }
    }
}
