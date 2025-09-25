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

    if (compareNumberStrings(yStr, "-3") < 0) return false;
    if (compareNumberStrings(yStr, "3") > 0) return false;

    return true;
}


function addToTable(x, y, r, result, currTime, execTime) {
    const tableBody = document.getElementById('output');
    const newRow = document.createElement('tr');
    const formattedY = typeof y === 'number' ? y.toFixed(3) : y;
    newRow.innerHTML = `
        <td>${x}</td>
        <td>${formattedY}</td>
        <td>${r}</td>
        <td>${result ? 'Попал' : 'Не попал'}</td>
        <td>${currTime}</td>
        <td>${execTime}</td>
    `;
    //tableBody.appendChild(newRow);
    tableBody.insertBefore(newRow, tableBody.firstChild);

}

function saveToLocalStorage(x, y, r, result, currTime, execTime) {
    const newItem = { x, y, r, result, currTime, execTime };
    const newData = JSON.parse(localStorage.getItem('tableData') || '[]');
    newData.push(newItem);
    localStorage.setItem('tableData', JSON.stringify(newData));
}

function loadFromLocalStorage() {
    const savedData = JSON.parse(localStorage.getItem('tableData') || '[]');
    savedData.forEach(item => {
        addToTable(item.x, item.y, item.r, item.result, item.currTime, item.execTime);
    });

    if (document.getElementById('output').rows.length === 0) {
        addPlaceholderRow();
    }
}
function clearLocalStorage() {
    localStorage.removeItem('tableData');
}

function clearTable() {
    const tableBody = document.getElementById('output');
    while (tableBody.rows.length > 0) {
        tableBody.removeChild(tableBody.lastChild);
    }
    addPlaceholderRow();
}

function resetForm() {
    const xRadios = document.querySelectorAll('input[name="x"]');
    xRadios.forEach(radio => radio.checked = false);

    const yInput = document.getElementById('y');
    if (yInput) {
        yInput.value = '';
    }

    const rRadios = document.querySelectorAll('input[name="r"]');
    rRadios.forEach(radio => radio.checked = false);
}
function removePlaceholderRow() {
    const tableBody = document.getElementById('output');
    const firstRow = tableBody.firstElementChild;

    if (firstRow && firstRow.tagName === 'TR') {
        const cells = firstRow.querySelectorAll('td');
        if (
            cells.length === 6 &&
            cells[0].textContent === 'Data for x' &&
            cells[1].textContent === 'Data for y' &&
            cells[2].textContent === 'Data for r' &&
            cells[3].textContent === 'Data for hit or not' &&
            cells[4].textContent === 'Data for current time' &&
            cells[5].textContent === 'Data for duration'
        ) {
            tableBody.removeChild(firstRow);
        }
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

function addPlaceholderRow() { // возвращение заглушки
    const tableBody = document.getElementById('output');

    if (tableBody.rows.length > 0) return;

    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td>Data for x</td>
        <td>Data for y</td>
        <td>Data for r</td>
        <td>Data for hit or not</td>
        <td>Data for current time</td>
        <td>Data for duration</td>
    `;
    tableBody.appendChild(newRow);
}
/*function setupClearButtonHandler() {
    const clearButton = document.getElementById('clear-btn');

    clearButton.addEventListener('click', () => {
        clearTable();
        clearLocalStorage();
    });
}*/
function setupClearButtonHandler() {
    const clearButton = document.getElementById('clear-btn');

    clearButton.addEventListener('click', () => {
        // Отправляем запрос на сервер для очистки БД
        fetch('fcgi-bin/labwork1.jar?clear=1', {
            method: 'GET',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Сервер вернул ошибку: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (data.status === "cleared") {
                    clearTable();
                    clearLocalStorage();
                } else {
                    throw new Error("Сервер не подтвердил очистку");
                }
            })
            .catch(error => {
                console.error("Ошибка очистки:", error);
                showFlashMessage(`Ошибка очистки: ${error.message}`);
            });
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


        const xElement = document.querySelector('input[name="x"]:checked');
        const yElement = document.querySelector('#y');
        const rElement = document.querySelector('input[name="r"]:checked');


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
            showFlashMessage("Вводите числовые значения");
            return;
        }

        if (!isYWithinBounds(yStr)) {
            showFlashMessage("Значение Y должно быть в интервале от -3 до 3");
            return;
        }
        const yVal = parseFloat(yStr);

        const validator = new InputValidator();
        validator.validate(xVal, yVal, rVal);


        if (validator.getResponseCode() === 1) {
            //console.log("Validation successful:", validator.getMessage());
            resetForm();
            // Отправляем запрос на сервер
            fetch(`fcgi-bin/labwork1.jar?x=${xVal}&y=${yStr}&r=${rVal}`, {
                method: 'GET',
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Сервер вернул ошибку: ${response.status}`);
                    }
                    return response.json();
                })
                .then(function (responseData) {
                    console.log("Ответ сервера:", responseData);
                    removePlaceholderRow();
                    addToTable(
                        xVal,
                        yVal,
                        rVal,
                        responseData.result,
                        responseData.curr_time,
                        responseData.exec_time
                    );

                    saveToLocalStorage(
                        xVal,
                        yVal,
                        rVal,
                        responseData.result,
                        responseData.curr_time,
                        responseData.exec_time
                    );

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

// инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    setupCheckButtonHandler();
    setupClearButtonHandler();
    setupCommaToDotInput();
    loadFromLocalStorage();
});