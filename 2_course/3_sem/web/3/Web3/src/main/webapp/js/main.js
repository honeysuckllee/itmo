function checkX() {
    const xInput = document.getElementById("xValue").value;
    x = parseFloat(xInput);
    console.log(xInput);
    if (![-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5].includes(x)) {
        alert("некорректный х");
        return;
    }
}

function checkY() {
    const yInput = document.getElementById("yValue").value;
    console.log(yInput);
    let y;
    try {
        y = new Decimal(yInput);
    } catch (e) {
        alert("Введите Y в формате числа (от -5 до 5)");
        return false;
    }

    // Проверяем, что y — это корректное число (не NaN)
    if (y.isNaN()) {
        alert("Введите Y в формате числа (от -5 до 5)");
        return false;
    }

    const min = new Decimal(-5);
    const max = new Decimal(5);

    if (y.greaterThanOrEqualTo(min) && y.lessThanOrEqualTo(max)) {
        return true;
    } else {
        alert("Введите Y в формате числа (от -5 до 5)");
        return false;
    }
}

function checkR() {
    const rInput = document.getElementById("rValue").value;
    console.log(rInput);
    let r;
    try {
        r = new Decimal(rInput);
    } catch (e) {
        alert("Введите R в формате числа (от 1 до 4)");
        return false;
    }

    // Проверяем, что y — это корректное число (не NaN)
    if (r.isNaN()) {
        alert("Введите R в формате числа (от 1 до 5)");
        return false;
    }

    const min = new Decimal(1);
    const max = new Decimal(5);

    if (r.greaterThanOrEqualTo(min) && r.lessThanOrEqualTo(max)) {
        return true;
    } else {
        alert("Введите R в формате числа (от 1 до 5)");
        return false;
    }
}


function validate(){
    return checkX() && checkY() && checkR();
}

function updateSvgValues(selectedR) {
    if (!selectedR) return;

    const r = parseFloat(selectedR);
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




document.addEventListener('DOMContentLoaded', () => {
    const rSelect = document.getElementById("rValue");
    const checkButton = document.querySelector('input[value="Проверить"]');

    rSelect.addEventListener('change', function() {
        updateSvgValues(this.value);
    });


    if (checkButton) {
        checkButton.addEventListener('click', (e) => {
            checkX();
            checkY();
            checkR();
        });
    } else {
        console.error("Кнопка 'Проверить' не найдена");
    }
});