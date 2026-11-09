<%@ page import="com.sabbir.mathquiz.model.QuizSession" %>
<%@ page import="com.sabbir.mathquiz.logic.QuizLogic" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    QuizSession quiz = (QuizSession) request.getAttribute("quiz");
    if (quiz == null) quiz = (QuizSession) session.getAttribute("mathQuiz");
    int questionIndex = quiz == null ? 0 : quiz.getQuestionIndex();
    int questionNumber = Math.min(questionIndex + 1, 4);
    int progress = quiz == null ? 0 : (quiz.getStage() == QuizSession.Stage.DONE ? 100 : questionIndex * 25);
    String stage = quiz == null ? "INPUT" : quiz.getStage().name();
    String error = (String) request.getAttribute("error");
    List<String> options = quiz == null ? java.util.Collections.emptyList() : quiz.getOptions();
    String context = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz - Math Quiz Arena</title>
    <link rel="stylesheet" href="<%= context %>/assets/style.css">
</head>
<body>
<div class="page-shell quiz-shell">
    <header class="topbar">
        <a class="brand" href="<%= context %>/quiz"><span class="brand-mark">π</span> Math Quiz Arena</a>
        <div class="score-chip"><span class="score-dot"></span> Score <strong><%= quiz.getScore() %> / 4</strong></div>
    </header>

    <main class="quiz-main">
        <% if ("DONE".equals(stage)) { %>
            <section class="result-card final-card">
                <div class="trophy-icon">🏆</div>
                <span class="eyebrow">QUIZ COMPLETED</span>
                <h1><%= quiz.getScore() == 4 ? "Perfect score!" : quiz.getScore() >= 2 ? "Good effort!" : "Keep practicing!" %></h1>
                <p>You have completed all four math challenges.</p>
                <div class="final-score"><strong><%= quiz.getScore() %></strong><span>/ 4</span></div>
                <div class="final-progress"><div style="width:<%= quiz.getScore() * 25 %>%"></div></div>
                <p class="score-caption"><%= quiz.getScore() * 25 %>% total score</p>
                <form action="<%= context %>/quiz" method="post">
                    <input type="hidden" name="action" value="restart">
                    <button class="btn btn-primary btn-large" type="submit">Play Again <span aria-hidden="true">↻</span></button>
                </form>
                <a class="text-link" href="<%= context %>/quiz">Back to home</a>
            </section>
        <% } else { %>
            <div class="quiz-intro">
                <a class="back-link" href="<%= context %>/quiz">← Home</a>
                <div class="progress-label"><span>YOUR PROGRESS</span><strong>Question <%= questionNumber %> of 4</strong></div>
                <div class="progress-track"><div class="progress-fill" style="width:<%= progress %>%"></div></div>
            </div>

            <section class="question-card">
                <div class="question-topline"><span class="question-number">QUESTION <%= questionNumber %></span><span class="mark-pill">+1 MARK</span></div>
                <h1><%= QuizLogic.getTitle(questionIndex) %></h1>
                <p class="question-description"><%= QuizLogic.getDescription(questionIndex) %></p>

                <% if (error != null && !error.isBlank()) { %>
                    <div class="alert alert-error"><%= escapeHtml(error) %></div>
                <% } %>

                <% if ("INPUT".equals(stage)) { %>
                    <form action="<%= context %>/quiz" method="post" class="quiz-form">
                        <input type="hidden" name="action" value="generate">
                        <label class="field-label" for="userInput"><%= QuizLogic.getInputLabel(questionIndex) %></label>
                        <input class="number-input" id="userInput" name="userInput" type="number"
                               step="<%= QuizLogic.getInputStep(questionIndex) %>"
                               min="<%= QuizLogic.getInputMin(questionIndex) %>"
                               max="<%= QuizLogic.getInputMax(questionIndex) %>"
                               placeholder="<%= questionIndex < 2 ? "e.g. 17" : "e.g. 30 or 45.5" %>"
                               required autofocus>
                        <% if (questionIndex == 1) { %><p class="input-hint">The divisor is fixed: 3.</p><% } %>
                        <% if (questionIndex >= 2) { %><p class="input-hint">Answer values are rounded to 3 decimal places.</p><% } %>
                        <button class="btn btn-primary btn-full" type="submit">Generate Answer Options <span aria-hidden="true">→</span></button>
                    </form>
                <% } else if ("OPTIONS".equals(stage)) { %>
                    <div class="input-summary"><span>Your input</span><strong><%= escapeHtml(quiz.getUserInput()) %><%= questionIndex >= 2 ? "°" : "" %></strong></div>
                    <form action="<%= context %>/quiz" method="post" class="quiz-form">
                        <input type="hidden" name="action" value="answer">
                        <span class="field-label">Choose the correct answer</span>
                        <div class="option-list">
                            <% for (int i = 0; i < options.size(); i++) {
                                String option = options.get(i);
                            %>
                                <label class="option-card">
                                    <input type="radio" name="selectedAnswer" value="<%= escapeHtml(option) %>" required>
                                    <span class="option-letter"><%= (char)('A' + i) %></span>
                                    <span class="option-value"><%= escapeHtml(option) %></span>
                                    <span class="option-radio"></span>
                                </label>
                            <% } %>
                        </div>
                        <button class="btn btn-primary btn-full" type="submit">Check Answer <span aria-hidden="true">→</span></button>
                    </form>
                <% } else if ("RESULT".equals(stage)) { %>
                    <div class="input-summary"><span>Your input</span><strong><%= escapeHtml(quiz.getUserInput()) %><%= questionIndex >= 2 ? "°" : "" %></strong></div>
                    <div class="answer-feedback <%= quiz.isLastAnswerCorrect() ? "feedback-correct" : "feedback-wrong" %>">
                        <div class="feedback-icon"><%= quiz.isLastAnswerCorrect() ? "✓" : "×" %></div>
                        <div>
                            <strong><%= quiz.isLastAnswerCorrect() ? "Correct answer! +1 mark" : "Not quite! No mark this time." %></strong>
                            <p>Your answer: <%= escapeHtml(quiz.getSelectedAnswer()) %></p>
                            <p>Correct answer: <b><%= escapeHtml(quiz.getCorrectAnswer()) %></b></p>
                        </div>
                    </div>
                    <div class="score-after"><span>Current score</span><strong><%= quiz.getScore() %> / 4</strong></div>
                    <form action="<%= context %>/quiz" method="post">
                        <input type="hidden" name="action" value="next">
                        <button class="btn btn-primary btn-full" type="submit"><%= questionIndex == 3 ? "View Final Score" : "Next Question" %> <span aria-hidden="true">→</span></button>
                    </form>
                <% } %>
            </section>
            <p class="quiz-footnote">One question at a time · Your score is saved in your current browser session.</p>
        <% } %>
    </main>
    <footer class="footer">Math Quiz Arena <span>Java Servlet + JSP · No database</span></footer>
</div>
</body>
</html>
