<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Хахулина Светлана Алексеевна">
    <title>Web programming laboratory work 1</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header>
    <div class="header-wrapper">
        <div class="info">var 636363 | Хахулина Светлана Алексеевна | P3206</div>
        <button id="theme-toggle" class="theme-toggle" aria-label="Переключить тему">
            <span class="icon sun">☀︎</span>
            <span class="icon moon">🌛︎︎</span>
            <span class="tooltip">Сменить тему</span>
        </button>
    </div>
</header>

<main class="container">
    <div class="graph">
        <svg width="440" height="440" id = "graph"
            data-last-r="${not empty sessionScope.results ? sessionScope.results[fn:length(sessionScope.results) - 1].r : 1}">

            <polygon points="220,220 308,220 220,132" fill="black" stroke="none"/>

            <defs>
                <clipPath id="quadrant3">
                    <rect x="44" y="220" width="176" height="176"/>
                </clipPath>
            </defs>
            <circle cx="220" cy="220" r="176" fill="black" stroke="none" clip-path="url(#quadrant3)"/>
            <rect x="220" y="220" width="88" height="176" fill="black" stroke="none"/>
            <line x1="220" y1="0" x2="220" y2="440" stroke="black" stroke-width="2"/>
            <line x1="0" y1="220" x2="440" y2="220" stroke="black" stroke-width="2"/>
            <line x1="215" y1="5" x2="225" y2="5" stroke="black" stroke-width="2"/>
            <line x1="220" y1="0" x2="215" y2="5" stroke="black" stroke-width="2"/>
            <line x1="220" y1="0" x2="225" y2="5" stroke="black" stroke-width="2"/>
            <text x="226" y="20">y</text>
            <line x1="435" y1="215" x2="435" y2="225" stroke="black" stroke-width="2"/>
            <line x1="440" y1="220" x2="435" y2="215" stroke="black" stroke-width="2"/>
            <line x1="440" y1="220" x2="435" y2="225" stroke="black" stroke-width="2"/>
            <text x="420" y="235">x</text>
            <line x1="215" y1="44" x2="225" y2="44" stroke="black" stroke-width="2"/>
            <line x1="215" y1="132" x2="225" y2="132" stroke="black" stroke-width="2"/>
            <line x1="215" y1="308" x2="225" y2="308" stroke="black" stroke-width="2"/>
            <line x1="215" y1="396" x2="225" y2="396" stroke="black" stroke-width="2"/>
            <line x1="308" y1="215" x2="308" y2="225" stroke="black" stroke-width="2"/>
            <line x1="396" y1="215" x2="396" y2="225" stroke="black" stroke-width="2"/>
            <line x1="132" y1="215" x2="132" y2="225" stroke="black" stroke-width="2"/>
            <line x1="44" y1="215" x2="44" y2="225" stroke="black" stroke-width="2"/>

            <text x="228" y="48" class="r-label">R</text>
            <text x="228" y="136" class="r-half-label">R/2</text>
            <text x="228" y="312" class="r-half-negative-label">-R/2</text>
            <text x="228" y="400" class="r-negative-label">-R</text>
            <text x="300" y="212" class="r-half-label-x">R/2</text>
            <text x="388" y="212" class="r-label-x">R</text>
            <text x="124" y="212" class="r-half-negative-label-x">-R/2</text>
            <text x="36" y="212" class="r-negative-label-x">-R</text>

            <c:if test="${not empty sessionScope.results}">
                <c:set var="lastR" value="${sessionScope.results[fn:length(sessionScope.results) - 1].r}" scope="page"/>
                <c:forEach var="item" items="${sessionScope.results}" varStatus="loop">

                        <circle class="point"
                                id = "${item.hit ? 'hit' : ''}"
                        cx="${220 + item.x * (176 / lastR)}"
                        cy="${220 - item.y * (176 / lastR)}"
                        x = "${item.x}"
                        y = "${item.y}"
                        r="5"/>
                 </c:forEach>
            </c:if>

        </svg>
    </div>

    <div class="form" id="input-form">
        <form>
            <div class="input-group">
                <label for="r">R:</label>
                <select id="r" name="r" class="select-input">
                    <option value="">Выберите R</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <div class="input-group">
                <label for="y">Y:</label>
                <input type="text" id="y" name="y">
            </div>
            <div class="input-group">
                <label for="x">X:</label>
                <select id="x" name="x" class="select-input">
                    <option value="">Выберите Х</option>
                    <option value="-3">-3</option>
                    <option value="-2">-2</option>
                    <option value="-1">-1</option>
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <div class="button-group">
                <input type="submit" value="Проверка">
                <input type="button" id="clear-btn" value="Очистить таблицу">
            </div>
            <div id="flash-message" class="flash-message"></div>
        </form>
    </div>
    <div class="table">
        <table class="resultTable">
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Попадание</th>
                <th>Время</th>
                <th>Время выполнения</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${sessionScope.results}"
                       varStatus="loop"
                       begin="${fn:length(sessionScope.results) > 5 ? fn:length(sessionScope.results) - 5 : 0}">
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
</main>

<script type="module" src="main.js"></script>
<script type="module" src="graph.js"></script>

</body>
</html>