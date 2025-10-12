<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <tr>
            <td>${x}</td>
            <td>${y}</td>
            <td>${r}</td>
            <td>${hit ? 'Да' : 'Нет'}</td>
            <td>${currentTime}</td>
            <td>${executionTimeMicros}</td>
        </tr>
    </table>

    <!-- История из HTTP-сессии -->
    <h2>История проверок</h2>
    <div id="info-table">
        <table>
            <thead>
            <tr>
                <th>x</th>
                <th>y</th>
                <th>R</th>
                <th>Время</th>
                <th>Выполнение</th>
                <th>Попадание</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${sessionScope.results}">
                <tr>
                    <td>${item.x.toPlainString()}</td>
                    <td>${item.y.toPlainString()}</td>
                    <td>${item.r.toPlainString()}</td>
                    <td>${item.timestamp}</td>
                    <td>${item.executionTimeMicros} мкс</td>
                    <td>${item.hit ? 'Да' : 'Нет'}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Кнопка "Назад" -->
<form action="index.jsp" method="get">
    <input class="back" type="submit" value="<-- Назад к форме" />
</form>
</body>
</html>