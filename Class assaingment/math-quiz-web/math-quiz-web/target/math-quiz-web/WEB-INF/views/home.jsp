<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Quiz Arena</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
<div class="page-shell">
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/quiz"><span class="brand-mark">π</span> Math Quiz Arena</a>
        <span class="topbar-note">JAVA SERVLET • JSP</span>
    </header>

    <main>
        <section class="hero-card">
            <div class="hero-copy">
                <span class="eyebrow">THINK • CALCULATE • ANSWER</span>
                <h1>Test your <span>math skills.</span></h1>
                <p>Four quick challenges. One mark for every correct answer. How high can you score?</p>
                <form action="${pageContext.request.contextPath}/quiz" method="post">
                    <input type="hidden" name="action" value="start">
                    <button class="btn btn-primary btn-large" type="submit">Start Quiz <span aria-hidden="true">→</span></button>
                </form>
                <div class="hero-meta"><span>4 questions</span><i></i><span>4 total marks</span><i></i><span>No database required</span></div>
            </div>
            <div class="hero-art" aria-hidden="true">
                <div class="orbit orbit-one"></div><div class="orbit orbit-two"></div>
                <div class="math-tile tile-main">∑</div><div class="math-tile tile-a">√x</div>
                <div class="math-tile tile-b">sin θ</div><div class="math-tile tile-c">π</div>
                <div class="math-tile tile-d">÷</div>
            </div>
        </section>

        <section class="section-block">
            <div class="section-heading"><div><span class="eyebrow">THE CHALLENGES</span><h2>Four topics. Four marks.</h2></div></div>
            <div class="challenge-grid">
                <article class="challenge-card"><div class="card-icon purple">01</div><h3>Prime or Not Prime</h3><p>Enter an integer and identify whether it is a prime number.</p><span class="card-tag">Number theory</span></article>
                <article class="challenge-card"><div class="card-icon blue">02</div><h3>Remainder</h3><p>Find the remainder when your number is divided by 3.</p><span class="card-tag">Modulus</span></article>
                <article class="challenge-card"><div class="card-icon orange">03</div><h3>Sine</h3><p>Enter an angle in degrees and identify its sine value.</p><span class="card-tag">Trigonometry</span></article>
                <article class="challenge-card"><div class="card-icon green">04</div><h3>Cosine</h3><p>Enter an angle in degrees and identify its cosine value.</p><span class="card-tag">Trigonometry</span></article>
            </div>
        </section>
    </main>
    <footer class="footer">Math Quiz Arena <span>Built with Java Servlet and JSP</span></footer>
</div>
</body>
</html>
