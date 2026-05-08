function setTimeFromServer() {
    const serverTimeElement = document.getElementById('indexform:serverTime');

    const serverTimeMillis = parseInt(serverTimeElement.textContent, 10);

    if (isNaN(serverTimeMillis)) {
        console.error("Не удалось получить время с сервера.");
        return;
    }

    const serverTime = new Date(serverTimeMillis);
    const seconds = serverTime.getSeconds();
    const minutes = serverTime.getMinutes();
    const hours = serverTime.getHours();
    const day = serverTime.getDate();
    const month = serverTime.getMonth() + 1;
    const year = serverTime.getFullYear();

    const formattedSeconds = seconds < 10 ? '0' + seconds : seconds;
    const formattedMinutes = minutes < 10 ? '0' + minutes : minutes;
    const formattedHours = hours < 10 ? '0' + hours : hours;
    const formattedDay = day < 10 ? '0' + day : day;
    const formattedMonth = month < 10 ? '0' + month : month;

    const formattedDateTime = "" + formattedDay + "." + formattedMonth + "." + year + " " + formattedHours + ":" + formattedMinutes + ":" + formattedSeconds;
    const divTimeElement = document.getElementById('date-time');
    divTimeElement.innerText = formattedDateTime;
}

function updateClockFromServer() {
    setTimeFromServer();
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', setTimeFromServer);
} else {
    setTimeFromServer();
}