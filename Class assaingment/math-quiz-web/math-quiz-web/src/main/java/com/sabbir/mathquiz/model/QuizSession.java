package com.sabbir.mathquiz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Stores one user's quiz state in the HTTP session. */
public class QuizSession implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Stage {
        INPUT, OPTIONS, RESULT, DONE
    }

    private int questionIndex;
    private int score;
    private Stage stage = Stage.INPUT;
    private String userInput = "";
    private String correctAnswer = "";
    private String selectedAnswer = "";
    private List<String> options = new ArrayList<>();
    private boolean lastAnswerCorrect;

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getScore() {
        return score;
    }

    public void addMark() {
        score++;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public boolean isLastAnswerCorrect() {
        return lastAnswerCorrect;
    }

    public void setLastAnswerCorrect(boolean lastAnswerCorrect) {
        this.lastAnswerCorrect = lastAnswerCorrect;
    }

    public void moveToNextQuestion() {
        questionIndex++;
        userInput = "";
        correctAnswer = "";
        selectedAnswer = "";
        options.clear();
        lastAnswerCorrect = false;
        stage = questionIndex >= 4 ? Stage.DONE : Stage.INPUT;
    }
}
