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

        if (!(yVal >= -3 && yVal <= 3)) {
            this.message = "Значение Y должно быть в интервале от -3 до 3";
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

function setupCommaToDotInput() { //замена запятой на точку.всё норм с курсором.ввести можно только разрешенные символы
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

        // Проверяем цифры, точку, запятую и минус
        if (!/^[0-9.,\-]$/.test(e.key)) {
            e.preventDefault();
            return;
        }

        const currentValue = this.value;
        const selectionStart = this.selectionStart;

        // Проверка для точки/запятой
        if (e.key === '.' || e.key === ',') {
            if (currentValue.includes('.')) {
                e.preventDefault();
            }
        }

        // Проверка для минуса
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
function setupClearButtonHandler() {
    const clearButton = document.getElementById('clear-btn');

    clearButton.addEventListener('click', () => {
        clearTable();
        clearLocalStorage();
    });
}

function showFlashMessage(message) {
    const flash = document.getElementById('flash-message');
    flash.textContent = message;
    flash.classList.add('show');
    setTimeout(() => flash.classList.remove('show'), 3000); // скроется через 3 сек
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
        const yVal = parseFloat(yElement.value);
        const rVal = parseFloat(rElement.value);

        if (isNaN(xVal) || isNaN(yVal) || isNaN(rVal)) {
            showFlashMessage("Вводите числовые значения");
            return;
        }

        const validator = new InputValidator();
        validator.validate(xVal, yVal, rVal);


        if (validator.getResponseCode() === 1) {
            //console.log("Validation successful:", validator.getMessage());
            resetForm();
            // Отправляем запрос на сервер
            fetch(`fcgi-app/?x=${xVal}&y=${yVal}&r=${rVal}`, {
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