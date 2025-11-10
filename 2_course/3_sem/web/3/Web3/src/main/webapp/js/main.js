function showFlashMessage(message) {
    const flash = document.getElementById('flash-message');
    if (!flash) return;
    flash.textContent = message;
    flash.classList.add('show');
    setTimeout(() => flash.classList.remove('show'), 3000);
}

function checkX() {
    const xInput = document.getElementById("xValue").value;
    x = parseFloat(xInput);
    if (![-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5].includes(x)) {
        showFlashMessage("X должен быть целым числом от -5 до 5");
        return false;
    }
    return true;
}

function checkY() {
    const yInput = document.getElementById("yValue").value;
    let y;
    try {
        y = new Decimal(yInput);
    } catch (e) {
        showFlashMessage("Введите Y в формате числа (от -5 до 5)");
        return false;
    }

    if (y.isNaN()) {
        showFlashMessage("Введите Y в формате числа (от -5 до 5)");
        return false;
    }

    const min = new Decimal(-5);
    const max = new Decimal(5);

    if (y.greaterThanOrEqualTo(min) && y.lessThanOrEqualTo(max)) {
        return true;
    } else {
        showFlashMessage("Y должен быть в диапазоне от -5 до 5");
        return false;
    }
}

function checkR() {
    const rInput = document.getElementById("rValue").value;
    let r;
    try {
        r = new Decimal(rInput);
    } catch (e) {
        showFlashMessage("Введите R в формате числа (от 1 до 4)");
        return false;
    }

    // Проверяем, что y — это корректное число (не NaN)
    if (r.isNaN()) {
        showFlashMessage("Введите R в формате числа (от 1 до 4)");
        return false;
    }

    const min = new Decimal(1);
    const max = new Decimal(4);

    if (r.greaterThanOrEqualTo(min) && r.lessThanOrEqualTo(max)) {
        return true;
    } else {
        showFlashMessage("R должен быть в диапазоне от 1 до 4");
        return false;
    }
}


function validate(){
    return checkX() && checkY() && checkR();
}

function updateSvgValues(selectedR) {
    if (!selectedR) return;

    const r = parseFloat(selectedR);
    if (isNaN(r)) return;
    const rHalf = (r / 2);

    document.querySelectorAll('.r-label, .r-label-x').forEach(el => {
        el.textContent = r;
    });

    document.querySelectorAll('.r-half-label, .r-half-label-x').forEach(el => {
        el.textContent = rHalf;
    });

    document.querySelectorAll('.r-half-negative-label, .r-half-negative-label-x').forEach(el => {
        el.textContent = '-' + rHalf;
    });

    document.querySelectorAll('.r-negative-label, .r-negative-label-x').forEach(el => {
        el.textContent = '-' + r;
    });
}

function isInArea(x, y, r) {
    if (x >= -r && x <= 0 && y >= 0 && y <= r / 2) {
        return true;
    }
    if (x <= 0 && y <= 0 && (x * x + y * y <= r * r)) {
        return true;
    }
    if (x >= 0 && y <= 0 && y >= x - r / 2) {
        return true;
    }
    return false;
}

function drawPointsOnGraph(jsonString, currentR) {
    const graph = document.getElementById('graph');
    if (!graph) {
        console.error("Элемент графика с id='graph' не найден!");
        return;
    }

    // 1. Очищаем старые точки
    const oldPoints = graph.querySelectorAll('circle.point');
    oldPoints.forEach(point => point.remove());

    let points;
    try {
        points = JSON.parse(jsonString);
    } catch (e) {
        console.error("Не удалось распарсить JSON:", e);
        return;
    }

    if (!Array.isArray(points) || points.length === 0) {
        return;
    }

    const r = parseFloat(currentR);
    if (isNaN(r) || r <= 0) {
        console.error("Текущий радиус R некорректен:", currentR);
        return;
    }

    points.forEach(pointData => {
        const { x, y } = pointData;

        const isHit = isInArea(x, y, r);

        const scaleFactor = 176 / r;
        const cx = 220 + x * scaleFactor;
        const cy = 220 - y * scaleFactor;

        const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');

        circle.setAttribute('cx', cx);
        circle.setAttribute('cy', cy);
        circle.setAttribute('r', '5'); // Радиус точки на графике
        circle.setAttribute('id', isHit ? 'hit' : ''); // Зеленый/Красный
        circle.setAttribute('stroke', 'white');
        circle.setAttribute('stroke-width', '1');
        circle.setAttribute('class', 'point'); // Класс для очистки

        graph.appendChild(circle);
    });
}

function redrawGraphWithCurrentR() {
    const jsonEl = document.getElementById('pointsJson');
    const jsonString = jsonEl.textContent || jsonEl.innerText;
    const currentR = document.getElementById('rValue').value;
    drawPointsOnGraph(jsonString, currentR);
}


document.addEventListener('DOMContentLoaded', () => {
    const rSelect = document.getElementById("rValue");
    const checkButton = document.querySelector('input[value="Проверить"]');

    // Первоначальная настройка при загрузке страницы
    const initialR = rSelect.value;
    if (checkR(initialR)) {
        updateSvgValues(initialR);
    } else {
        rSelect.value = 4;
        updateSvgValues(4);
    }
    redrawGraphWithCurrentR();

    rSelect.addEventListener('change', function() {
        if (checkR(this.value)) {
            updateSvgValues(this.value);
            redrawGraphWithCurrentR();
        }else {
            rSelect.value = 4;
            updateSvgValues(4);
            redrawGraphWithCurrentR();
        }
    });
    if (checkButton) {
        checkButton.addEventListener('click', (e) => {
            if (!validate()) {
                e.preventDefault();
            }
        });
    } else {
        console.error("Кнопка 'Проверить' не найдена");
    }
});