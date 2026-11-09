# Math Quiz Arena — Java Servlet Web Application

A four-question math quiz web application built with Java 17, Jakarta Servlet 6, JSP, HTML, and CSS. It is designed to run on **Apache Tomcat 10.1**.

## 1.Project setup and interface

1. **Prime or Not Prime:** Enter a whole number and choose Prime or Not Prime.
2. **Remainder:** Enter a non-negative whole number and select its remainder when divided by 3. The other two choices are `remainder + 3` and `remainder - 2`.
3. **Sine:** Enter an angle in degrees and choose its sine value. The two distractors are the correct value + 3 and - 2.
4. **Cosine:** Enter an angle in degrees and choose its cosine value. The two distractors are the correct value + 3 and - 2.

Each correct answer adds 1 mark. The maximum score is 4. Sine/cosine values are rounded to three decimal places. Choices are shuffled for each generated question.

## Requirements

- JDK 17 or later
- Apache Tomcat 10.1.x (Jakarta Servlet 6.0)
- Maven 3.8 or later

No MySQL, database, Gson, or Swing is required. Each player's quiz state and score are kept in their own HTTP session; the score is not stored permanently after the session expires.

##  Configure and build

Open a terminal in the project root (the folder containing `pom.xml`) and run:

```bash
mvn clean package
```

The WAR file will be created at:

```text
target/math-quiz-web.war
```

##  Deploy to Tomcat

1. Stop Tomcat.
2. Copy `target/math-quiz-web.war` into Tomcat's `webapps` directory.
3. Start Tomcat.
4. Open this URL in your browser:

```text
http://localhost:8080/math-quiz-web/
```

If your Tomcat uses a different port, replace `8080` with that port.

##  Run from an IDE

Import the folder as an existing Maven project in IntelliJ IDEA or Eclipse. Configure a local Tomcat 10.1 server and deploy the project as a WAR / exploded WAR. In VS Code, build with `mvn clean package` and deploy the WAR to Tomcat as above.

## Important input rules

- Prime and remainder questions accept whole numbers from 0 to 1,000,000,000.
- Remainder uses divisor 3 (fixed).
- Sine and cosine accept degree values from -1,000,000 to 1,000,000, including decimals.
- Trigonometric options are rounded to 3 decimal places.

## Project structure

```text
math-quiz-web/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/sabbir/mathquiz/
        │   ├── logic/QuizLogic.java
        │   ├── model/QuizSession.java
        │   └── servlet/QuizServlet.java
        └── webapp/
            ├── index.jsp
            ├── assets/style.css
            └── WEB-INF/views/
                ├── home.jsp
                └── quiz.jsp
```

## 2. Prime Number Quiz 

User enters an integer.

The application checks whether the number is Prime or Not Prime.

Two answer options are generated.

Correct answer awards 1 mark.

## 3. Modulus Quiz 

User enters a non-negative integer.

The application calculates the remainder when divided by 3.

Three options are generated:

Correct remainder

Remainder + 3

Remainder - 2

Correct answer awards 1 mark.

## 4. Trigonometric Quiz 

User enters an angle in degrees.

The application calculates:

Sine value: sin(θ)

Cosine value: cos(θ)

Multiple-choice options are generated automatically.

Answers are calculated using Java's built-in Math library.

Both questions award 1 mark each.

## 5. Game Logic and Score Calculation 

Tracks the user's quiz progress.

Checks selected answers automatically.

Awards 1 mark for each correct answer.

Displays feedback for correct and incorrect answers.

Shows the final score after completing all questions.

## 6. Database Integration 

JDBC connectivity with MySQL.

Store player information and quiz scores.

Retrieve saved quiz results.

Maintain player records.

## 7. Top 10 Leaderboard 

Display the top 10 players based on their scores.

Rank players according to their performance.

Retrieve leaderboard information from the database.

## 8. screenshots

<img width="809" height="627" alt="image" src="https://github.com/user-attachments/assets/c65f50bc-1364-440e-a881-870aec8ff24f" />

<img width="825" height="492" alt="image" src="https://github.com/user-attachments/assets/f81f7af9-5ba0-4e70-b1a6-6e63635c4cde" />

<img width="806" height="460" alt="image" src="https://github.com/user-attachments/assets/206c92be-18b3-4409-a3e4-6d71ab4d168d" />

<img width="822" height="642" alt="image" src="https://github.com/user-attachments/assets/e7076b59-0858-4580-b9b3-61acb605966d" />

<img width="814" height="608" alt="image" src="https://github.com/user-attachments/assets/cd5711fa-390b-4a29-88d1-3f06a10159bc" />

<img width="798" height="690" alt="image" src="https://github.com/user-attachments/assets/2244ff16-1680-4e39-b3d2-f6cd91e6176a" />





