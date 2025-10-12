<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Результат проверки</title>
</head>
<body>
<div class="particles" id="particles"></div>
<div id="content">
    <h2>Результат текущей проверки</h2>
    <table>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание</th>
            <th>Время запроса</th>
            <th>Время выполнения</th>
        </tr>
        <c:set var="lastIndex" value="${fn:length(sessionScope.results) - 1}"/>
        <c:if test="${lastIndex >= 0}">
            <c:set var="item" value="${sessionScope.results[lastIndex]}"/>
            <tr>
                <td>${item.x.toPlainString()}</td>
                <td>${item.y.toPlainString()}</td>
                <td>${item.r.toPlainString()}</td>
                <td>${item.hit ? 'Да' : 'Нет'}</td>
                <td>${item.timestamp}</td>
                <td>${item.executionTimeMicros} мкс</td>
            </tr>
        </c:if>
    </table>

    <!-- История из HTTP-сессии -->
    <h2>История проверок</h2>
    <div id="info-table" class="table-wrapper">
        <table class="result-table">
            <thead>
            <tr>
                <th>x</th>
                <th>y</th>
                <th>R</th>
                <th>Попадание</th>
                <th>Время запроса</th>
                <th>Время выполнения</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${sessionScope.results}">
                <tr>
                    <td>${item.x.toPlainString()}</td>
                    <td>${item.y.toPlainString()}</td>
                    <td>${item.r.toPlainString()}</td>
                    <td>${item.hit ? 'Да' : 'Нет'}</td>
                    <td>${item.timestamp}</td>
                    <td>${item.executionTimeMicros} мкс</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="button-group">
<form action="index.jsp" method="get">
    <input class="button-group" type="submit" value="Назад к форме" />
</form>
</div>
</body>
<script type="module" src="result.js"></script>
</html>