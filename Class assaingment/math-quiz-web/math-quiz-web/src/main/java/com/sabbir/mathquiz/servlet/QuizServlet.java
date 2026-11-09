package com.sabbir.mathquiz.servlet;

import com.sabbir.mathquiz.logic.QuizLogic;
import com.sabbir.mathquiz.model.QuizSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "QuizServlet", urlPatterns = {"/quiz"})
public class QuizServlet extends HttpServlet {
    private static final String SESSION_KEY = "mathQuiz";
    private static final String HOME_PAGE = "/WEB-INF/views/home.jsp";
    private static final String QUIZ_PAGE = "/WEB-INF/views/quiz.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        QuizSession quiz = getQuiz(request.getSession(false));
        if (quiz == null) {
            request.getRequestDispatcher(HOME_PAGE).forward(request, response);
        } else {
            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher(QUIZ_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession(true);
        QuizSession quiz = getQuiz(httpSession);

        if ("start".equals(action) || "restart".equals(action)) {
            quiz = new QuizSession();
            httpSession.setAttribute(SESSION_KEY, quiz);
            showQuiz(request, response, quiz);
            return;
        }

        if (quiz == null) {
            response.sendRedirect(request.getContextPath() + "/quiz");
            return;
        }

        switch (action == null ? "" : action) {
            case "generate" -> generateOptions(request, quiz);
            case "answer" -> submitAnswer(request, quiz);
            case "next" -> {
                if (quiz.getStage() == QuizSession.Stage.RESULT) {
                    quiz.moveToNextQuestion();
                }
            }
            default -> request.setAttribute("error", "Unknown action. Please try again.");
        }

        showQuiz(request, response, quiz);
    }

    private void generateOptions(HttpServletRequest request, QuizSession quiz) {
        if (quiz.getStage() != QuizSession.Stage.INPUT) {
            return;
        }

        try {
            QuizLogic.AnswerSet answerSet = QuizLogic.createAnswerSet(
                    quiz.getQuestionIndex(), request.getParameter("userInput"));
            quiz.setUserInput(answerSet.input());
            quiz.setCorrectAnswer(answerSet.correctAnswer());
            quiz.setOptions(answerSet.options());
            quiz.setStage(QuizSession.Stage.OPTIONS);
            request.setAttribute("error", null);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }

    private void submitAnswer(HttpServletRequest request, QuizSession quiz) {
        if (quiz.getStage() != QuizSession.Stage.OPTIONS) {
            return;
        }

        String selected = request.getParameter("selectedAnswer");
        if (selected == null || !quiz.getOptions().contains(selected)) {
            request.setAttribute("error", "Please select one of the available options.");
            return;
        }

        quiz.setSelectedAnswer(selected);
        boolean correct = selected.equals(quiz.getCorrectAnswer());
        quiz.setLastAnswerCorrect(correct);
        if (correct) {
            quiz.addMark();
        }
        quiz.setStage(QuizSession.Stage.RESULT);
    }

    private void showQuiz(HttpServletRequest request, HttpServletResponse response, QuizSession quiz)
            throws ServletException, IOException {
        request.setAttribute("quiz", quiz);
        request.getRequestDispatcher(QUIZ_PAGE).forward(request, response);
    }

    private QuizSession getQuiz(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object value = session.getAttribute(SESSION_KEY);
        return value instanceof QuizSession ? (QuizSession) value : null;
    }
}
