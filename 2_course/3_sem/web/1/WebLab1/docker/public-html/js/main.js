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

        // Если все проверки пройдены
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
    newRow.innerHTML = `
        <td>${x}</td>
        <td>${y}</td>
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
}
function clearLocalStorage() {
    localStorage.removeItem('tableData');
}

function clearTable() {
    const tableBody = document.getElementById('output');
    while (tableBody.rows.length > 0) {
        tableBody.removeChild(tableBody.lastChild);
    }
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

function setupCheckButtonHandler() {
    const checkButton = document.querySelector('input[value="Проверка"]');

    checkButton.addEventListener('click', (e) => {
        e.preventDefault();
        
        // Получаем элементы формы
        const xElement = document.querySelector('input[name="x"]:checked');
        const yElement = document.querySelector('#y');
        const rElement = document.querySelector('input[name="r"]:checked');
        
        // Проверяем, что все поля заполнены
        if (!xElement) {
            alert("Выберите значение X");
            return;
        }
        
        if (!yElement || yElement.value.trim() === "") {
            alert("Введите значение Y");
            return;
        }
        
        if (!rElement) {
            alert("Выберите значение R");
            return;
        }

        const xVal = parseFloat(xElement.value);
        const yVal = parseFloat(yElement.value);
        const rVal = parseFloat(rElement.value);

        if (isNaN(xVal) || isNaN(yVal) || isNaN(rVal)) {
            alert("Вводите числовые значения");
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

                saveToLocalStorage(
                    xVal,
                    yVal,
                    rVal,
                    responseData.result,
                    responseData.curr_time,
                    responseData.exec_time
                );
                clearTable();
                loadFromLocalStorage();
            })
            .catch(error => {
                console.error("Ошибка:", error);
                alert(`Ошибка запроса: ${error.message}`);
            });
        } else {
            console.error("Validation failed:", validator.getMessage());
            alert(validator.getMessage());
        }
    });
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    setupCheckButtonHandler();
    clearLocalStorage();
});