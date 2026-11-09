# Math Quiz Arena — Java Servlet Web Application

A four-question math quiz web application built with Java 17, Jakarta Servlet 6, JSP, HTML, and CSS. It is designed to run on **Apache Tomcat 10.1**.

## Features

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

## 1. Configure and build

Open a terminal in the project root (the folder containing `pom.xml`) and run:

```bash
mvn clean package
```

The WAR file will be created at:

```text
target/math-quiz-web.war
```

## 2. Deploy to Tomcat

1. Stop Tomcat.
2. Copy `target/math-quiz-web.war` into Tomcat's `webapps` directory.
3. Start Tomcat.
4. Open this URL in your browser:

```text
http://localhost:8080/math-quiz-web/
```

If your Tomcat uses a different port, replace `8080` with that port.

## 3. Run from an IDE

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

## How marking works

The server generates the answer choices and stores the correct answer in the user's HTTP session. When an answer is submitted, the Servlet checks it against the stored choices and awards one mark only once for that question. No client-side score value is trusted.
