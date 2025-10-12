<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <div class="info">var 467922 | Хахулина Светлана Алексеевна | P3206</div>
        <button id="theme-toggle" class="theme-toggle" aria-label="Переключить тему">
            <span class="icon sun">☀︎</span>
            <span class="icon moon">🌛︎︎</span>
            <span class="tooltip">Сменить тему</span>
        </button>
    </div>
</header>

<main class="container">
    <div class="graph">
        <svg viewBox="0 0 480 480" preserveAspectRatio="xMidYMid meet" width="100%" height="100%">
            <rect x="44" y="44" width="176" height="176" fill="black" stroke="none"/>
            <polygon points="220,220 308,220 220,132" fill="black" stroke="none"/>

            <!-- ✅ 3-я четверть: четверть окружности радиусом R -->
            <defs>
                <clipPath id="quadrant3">
                    <rect x="44" y="220" width="176" height="176"/>
                </clipPath>
            </defs>
            <circle cx="220" cy="220" r="176" fill="black" stroke="none" clip-path="url(#quadrant3)"/>

            <!-- ✅ 4-я четверть: прямоугольник (0,0) — (R/2,0) — (R/2,-R) — (0,-R) -->
            <rect x="220" y="220" width="88" height="176" fill="black" stroke="none"/>

            <!-- Оси и подписи — как в оригинале, рисуются ПОСЛЕДНИМИ -->
            <line x1="220" y1="0" x2="220" y2="440" stroke="black" stroke-width="2"/>
            <line x1="0" y1="220" x2="440" y2="220" stroke="black" stroke-width="2"/>

            <!-- Стрелки осей -->
            <line x1="215" y1="5" x2="225" y2="5" stroke="black" stroke-width="2"/>
            <line x1="220" y1="0" x2="215" y2="5" stroke="black" stroke-width="2"/>
            <line x1="220" y1="0" x2="225" y2="5" stroke="black" stroke-width="2"/>
            <text x="226" y="20">y</text>

            <line x1="435" y1="215" x2="435" y2="225" stroke="black" stroke-width="2"/>
            <line x1="440" y1="220" x2="435" y2="215" stroke="black" stroke-width="2"/>
            <line x1="440" y1="220" x2="435" y2="225" stroke="black" stroke-width="2"/>
            <text x="420" y="235">x</text>

            <!-- Метки на осях -->
            <line x1="215" y1="44" x2="225" y2="44" stroke="black" stroke-width="2"/>
            <text x="228" y="48">R</text>
            <line x1="215" y1="132" x2="225" y2="132" stroke="black" stroke-width="2"/>
            <text x="228" y="136">R/2</text>
            <line x1="215" y1="308" x2="225" y2="308" stroke="black" stroke-width="2"/>
            <text x="228" y="312">-R/2</text>
            <line x1="215" y1="396" x2="225" y2="396" stroke="black" stroke-width="2"/>
            <text x="228" y="400">-R</text>
            <line x1="308" y1="215" x2="308" y2="225" stroke="black" stroke-width="2"/>
            <text x="300" y="212">R/2</text>
            <line x1="396" y1="215" x2="396" y2="225" stroke="black" stroke-width="2"/>
            <text x="388" y="212">R</text>
            <line x1="132" y1="215" x2="132" y2="225" stroke="black" stroke-width="2"/>
            <text x="124" y="212">-R/2</text>
            <line x1="44" y1="215" x2="44" y2="225" stroke="black" stroke-width="2"/>
            <text x="36" y="212">-R</text>
        </svg>
    </div>

    <div class="form" id="input-form">
        <form>
            <div class="input-group">
                <label for="x">X:</label>
                <div id="x" class="radio-group">
                    <label><input type="radio" name="x" value="-3">-3</label>
                    <label><input type="radio" name="x" value="-2">-2</label>
                    <label><input type="radio" name="x" value="-1">-1</label>
                    <label><input type="radio" name="x" value="0">0</label>
                    <label><input type="radio" name="x" value="1">1</label>
                    <label><input type="radio" name="x" value="2">2</label>
                    <label><input type="radio" name="x" value="3">3</label>
                    <label><input type="radio" name="x" value="4">4</label>
                    <label><input type="radio" name="x" value="5">5</label>
                </div>
            </div>
            <div class="input-group">
                <label for="y">Y:</label>
                <input type="text" id="y" name="y">
            </div>
            <div class="input-group">
                <label for="r">R:</label>
                <div id="r" class="radio-group">
                    <label><input type="radio" name="r" value="1">1</label>
                    <label><input type="radio" name="r" value="2">2</label>
                    <label><input type="radio" name="r" value="3">3</label>
                    <label><input type="radio" name="r" value="4">4</label>
                    <label><input type="radio" name="r" value="5">5</label>
                </div>
            </div>
            <div class="button-group">
                <input type="submit" value="Проверка">
            </div>
            <div id="flash-message" class="flash-message"></div>
        </form>
    </div>

</main>

<script type="module" src="main.js"></script>
</body>
</html>