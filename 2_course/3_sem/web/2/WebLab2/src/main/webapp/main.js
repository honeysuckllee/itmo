export default class InputValidator {
    constructor() {
        this.responseCode = 1; // 1 - успех, 0 - ошибка
        this.message = "Значения допустимы";
    }

    validate(xVal, yVal, rVal) {
        if (![-3, -2, -1, 0, 1, 2, 3, 4, 5].includes(xVal)) {
            this.message = "Значение X должно быть: -3, -2, -1, 0, 1, 2, 3, 4, 5";
            this.responseCode = 0;
            return;
        }

        if (![1, 2, 3, 4, 5].includes(rVal)) {
            this.message = "Значение R должно быть: 1, 2, 3, 4, 5";
            this.responseCode = 0;
            return;
        }

        this.responseCode = 1;
        this.message = "Значения корректны";
    }

    getResponseCode() {
        return this.responseCode;
    }

    getMessage() {
        return this.message;
    }
}

function compareNumberStrings(a, b) {
    const normalize = (s) => {
        s = s.trim();
        if (!s.includes('.')) return s;
        let [intPart, fracPart] = s.split('.');
        fracPart = fracPart.replace(/0+$/, ''); // убираем конечные нули
        return fracPart === '' ? intPart : `${intPart}.${fracPart}`;
    };

    a = normalize(a);
    b = normalize(b);

    const aIsNegative = a.startsWith('-');
    const bIsNegative = b.startsWith('-');

    if (aIsNegative && !bIsNegative) return -1;
    if (!aIsNegative && bIsNegative) return 1;

    const aAbs = aIsNegative ? a.slice(1) : a;
    const bAbs = bIsNegative ? b.slice(1) : b;

    const [aInt, aFrac = ''] = aAbs.split('.');
    const [bInt, bFrac = ''] = bAbs.split('.');
    if (aInt.length !== bInt.length) {
        return (aIsNegative ? -1 : 1) * (aInt.length - bInt.length);
    }
    if (aInt !== bInt) {
        return (aIsNegative ? -1 : 1) * (aInt > bInt ? 1 : -1);
    }

    const maxLength = Math.max(aFrac.length, bFrac.length);
    for (let i = 0; i < maxLength; i++) {
        const aDigit = i < aFrac.length ? aFrac[i] : '0';
        const bDigit = i < bFrac.length ? bFrac[i] : '0';
        if (aDigit !== bDigit) {
            return (aIsNegative ? -1 : 1) * (aDigit > bDigit ? 1 : -1);
        }
    }

    return 0;
}
function isYWithinBounds(yStr) {
    if (typeof yStr !== 'string') return false;
    yStr = yStr.trim();
    const validNumberRegex = /^-?\d+(\.\d+)?$/;
    if (!validNumberRegex.test(yStr)) return false;

    if (compareNumberStrings(yStr, "-5") < 0) return false;
    if (compareNumberStrings(yStr, "5") > 0) return false;

    return true;
}

function clearTable() {

    const formData = new URLSearchParams();
    formData.append('path', '/cleartable');

    // Отправляем запрос
    fetch('controller', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Сервер вернул ошибку: ${response.status}`);
            }
            if (response.ok) {
                showFlashMessage("Таблица очищена");
            }
        })
        .catch(function(error) {
            showFlashMessage("Ошибка очистки таблицы");
        });
}

function clearResultTable() {
    const tableBody = document.querySelector('.resultTable tbody');
    if (tableBody) {
        tableBody.innerHTML = '';
    }
}
function clearAllPoints() {
    const points = document.querySelectorAll('circle.point');
    points.forEach(circle => circle.remove());
}

function resetForm() {
    const xSelect = document.querySelector('select[name="x"]');
    const rSelect = document.querySelector('select[name="r"]');

    if (xSelect) xSelect.value = "";
    if (rSelect) rSelect.value = "";

    const yInput = document.getElementById('y');
    if (yInput) {
        yInput.value = '';
    }
}

function setupCommaToDotInput() { //замена запятой на точку.ввести можно только разрешенные символы
    const inputElement = document.getElementById('y');
    inputElement.addEventListener('input', function (e) {
        let value = this.value;
        let newValue = value.replace(/,/g, '.');
        const dotCount = (newValue.match(/\./g) || []).length;
        if (dotCount > 1){
            newValue = newValue.replace(/\./g, '');
            const firstDotIndex = value.indexOf('.');
            if (firstDotIndex !== -1) {
                newValue = newValue.slice(0, firstDotIndex) + '.' + newValue.slice(firstDotIndex);
            }
        }

        const minusCount = (newValue.match(/-/g) || []).length;
        if (minusCount > 1) {
            newValue = newValue.replace(/-/g, '');
            if (value.startsWith('-')) {
                newValue = '-' + newValue;
            }
        }

        if (newValue.includes('-') && !newValue.startsWith('-')) {
            newValue = newValue.replace(/-/g, '');
        }

        if (newValue !== value) {
            this.value = newValue;
        }
    });

    inputElement.addEventListener('keydown', function (e) {

        const allowedKeys = [ // цифры, точки, запятые, стрелки, Backspace, Delete, Tab, Ctrl+a, Ctrl+v, Ctrl+c
            'Backspace', 'Delete', 'Tab', 'Escape', 'Enter',
            'ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown',
            'Home', 'End'
        ];
        const isModifierKey = e.ctrlKey || e.metaKey;

        if (isModifierKey || allowedKeys.includes(e.key)) {
            return;
        }

        if (!/^[0-9.,\-]$/.test(e.key)) {
            e.preventDefault();
            return;
        }

        const currentValue = this.value;
        const selectionStart = this.selectionStart;

        if (e.key === '.' || e.key === ',') {
            if (currentValue.includes('.')) {
                e.preventDefault();
            }
        }

        if (e.key === '-') {
            if (currentValue.includes('-') || selectionStart !== 0) {
                e.preventDefault();
            }
        }
    });
}

function showFlashMessage(message) {
    const flash = document.getElementById('flash-message');
    flash.textContent = message;
    flash.classList.add('show');
    setTimeout(() => flash.classList.remove('show'), 3000);
}
function setupCheckButtonHandler() {
    const checkButton = document.querySelector('input[value="Проверка"]');

    checkButton.addEventListener('click', (e) => {
        e.preventDefault();
        document.getElementById('flash-message').classList.remove('show');


        const xElement = document.querySelector('select[name="x"]');
        const yElement = document.querySelector('#y');
        const rElement = document.querySelector('select[name="r"]');



        if (!xElement) {
            showFlashMessage("Выберите значение X");
            return;
        }

        if (!yElement || yElement.value.trim() === "") {
            showFlashMessage("Введите значение Y");
            return;
        }

        if (!rElement) {
            showFlashMessage("Выберите значение R");
            return;
        }

        const xVal = parseFloat(xElement.value);
        const yStr = yElement.value.trim();
        const rVal = parseFloat(rElement.value);

        if (isNaN(xVal) || isNaN(rVal)) {
            showFlashMessage("Вводите числовые значения!");
            return;
        }

        if (!isYWithinBounds(yStr)) {
            showFlashMessage("Значение Y должно быть в интервале от -5 до 5");
            return;
        }
        const yVal = parseFloat(yStr);

        const validator = new InputValidator();
        validator.validate(xVal, yVal, rVal);


        const formData = new URLSearchParams();
        formData.append('x', "" + xVal);
        formData.append('y', yStr);
        formData.append('r', "" + rVal);
        formData.append("path", "/areacheck");

        if (validator.getResponseCode() === 1) {
            resetForm();
            // Отправляем запрос на сервер
            fetch(`controller`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                },
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Сервер вернул ошибку: ${response.status}`);
                    }
                    if (response.ok) {
                        window.location.href = "result.jsp";
                    }
                })

                .catch(error => {
                    console.error("Ошибка:", error);
                    showFlashMessage(`Ошибка запроса: ${error.message}`);
                });
        } else {
            console.error("Validation failed:", validator.getMessage());
            showFlashMessage(validator.getMessage());
        }
    });
}


function updateSvgValues(selectedR) {
    if (!selectedR) return;

    const r = parseFloat(selectedR);
    const rHalf = (r / 2).toFixed(1);

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

function updateSvgPoint(selectedR) {
    if (!selectedR) return;

    const r = parseFloat(selectedR);
    if (isNaN(r) || r === 0) return;

    const scale = 176 / r;
    const points = document.querySelectorAll('circle.point');

    points.forEach(function(circle) {
        const x = parseFloat(circle.getAttribute('x'));
        const y = parseFloat(circle.getAttribute('y'));

        if (isNaN(x) || isNaN(y)) return;

        // Обновляем координаты сразу
        const cx = 220 + x * scale;
        const cy = 220 - y * scale;
        circle.setAttribute('cx', cx.toFixed(2));
        circle.setAttribute('cy', cy.toFixed(2));

        // Формируем данные для запроса
        const formData = new URLSearchParams();
        formData.append('x', String(x));
        formData.append('y', String(y));
        formData.append('r', String(r));
        formData.append('path', '/areacheckjs');

        // Отправляем запрос
        fetch('controller', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            body: formData
        })
            .then(function(response) {
                return response.text();
            })
            .then(function(text) {
                const isHit = text.trim().toLowerCase() === 'true';
                circle.id = isHit ? 'hit' : '';
            })
            .catch(function(error) {
                console.warn('Ошибка проверки точки:', x, y, r, error);
                circle.id = ''; // по умолчанию — не попадание
            });
    });
}

function getLastRFromSVG() {
    const svg = document.getElementById('graph');
    if (!svg) return 1;

    const lastR = svg.getAttribute('data-last-r');
    return lastR ? parseInt(lastR) : 1;
}

// инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    setupCheckButtonHandler();
    setupCommaToDotInput();

    const themeToggle = document.getElementById('theme-toggle');
    const htmlElement = document.documentElement; // <html>

    const savedTheme = localStorage.getItem('theme') || 'light';
    htmlElement.setAttribute('data-theme', savedTheme);

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            const currentTheme = htmlElement.getAttribute('data-theme');
            const newTheme = currentTheme === 'light' ? 'dark' : 'light';
            htmlElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
        });
    }

    const rSelect = document.getElementById('r');
    rSelect.addEventListener('change', function() {
        updateSvgValues(this.value);
        updateSvgPoint(this.value);
    });
    const rVal = getLastRFromSVG();
    rSelect.value = rVal;
    console.log("rVal= ", rVal);

    // Инициализация при загрузке
    if (rSelect.value) {
        updateSvgValues(rSelect.value);
        updateSvgPoint(rSelect.value);
    }
    const checkButton = document.querySelector('input[value="Очистить таблицу"]');
    checkButton.addEventListener('click', (e) => {
        e.preventDefault();
        clearTable();
        clearResultTable();
        clearAllPoints();
    });
});