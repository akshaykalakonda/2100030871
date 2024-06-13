<%@ page contentType="text/html; charset=ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>  


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title th:text="'Movie Details - ' + ${movie['Title']}">Movie Details</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f9;
    color: #333;
    margin: 0;
    padding: 20px;
}

.container {
    max-width: 600px;
    margin: auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
    text-align: center;
    color: #333;
}

p {
    font-size: 16px;
    margin: 8px 0;
}

img {
    display: block;
    margin: 20px auto;
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

ul {
    list-style-type: none;
    padding: 0;
}

ul li {
    font-size: 14px;
    margin: 4px 0;
}

</style>
<body>
    <div class="container">
        <h1 th:text="${movie['Title']}">Title</h1>
        <img th:src="${movie['Poster']}" alt="Movie Poster">
        <p><strong>Year:</strong> <span th:text="${movie['Year']}">Year</span></p>
        <p><strong>Rated:</strong> <span th:text="${movie['Rated']}">Rated</span></p>
        <p><strong>Released:</strong> <span th:text="${movie['Released']}">Released</span></p>
        <p><strong>Runtime:</strong> <span th:text="${movie['Runtime']}">Runtime</span></p>
        <p><strong>Genre:</strong> <span th:text="${movie['Genre']}">Genre</span></p>
        <p><strong>Director:</strong> <span th:text="${movie['Director']}">Director</span></p>
        <p><strong>Writer:</strong> <span th:text="${movie['Writer']}">Writer</span></p>
        <p><strong>Actors:</strong> <span th:text="${movie['Actors']}">Actors</span></p>
        <p><strong>Plot:</strong> <span th:text="${movie['Plot']}">Plot</span></p>
        <p><strong>Language:</strong> <span th:text="${movie['Language']}">Language</span></p>
        <p><strong>Country:</strong> <span th:text="${movie['Country']}">Country</span></p>
        <p><strong>Awards:</strong> <span th:text="${movie['Awards']}">Awards</span></p>
        <p><strong>IMDB Rating:</strong> <span th:text="${movie['imdbRating']}">Rating</span></p>
        <p><strong>IMDB Votes:</strong> <span th:text="${movie['imdbVotes']}">Votes</span></p>

        <div>
            <strong>Ratings:</strong>
            <ul>
                <li th:each="rating : ${movie['Ratings']}">
                    <span th:text="${rating['Source']}">Source</span>: <span th:text="${rating['Value']}">Value</span>
                </li>
            </ul>
        </div>
    </div>
</body>
</html>