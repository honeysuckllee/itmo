function setClockFromServer() {
        const serverTimeElement = document.getElementById('indexform:serverTime');

        const serverTimeMillis = parseInt(serverTimeElement.textContent, 10);

        if (isNaN(serverTimeMillis)) {
            console.error("Не удалось получить время с сервера.");
            return;
        }

        const serverTime = new Date(serverTimeMillis);
        const seconds = serverTime.getSeconds();
        const minutes = serverTime.getMinutes();
        const hours = serverTime.getHours() % 12;

        // Углы поворота
        const secondDegrees = (seconds / 60) * 360;
        const minuteDegrees = ((minutes + seconds / 60) / 60) * 360;
        const hourDegrees = ((hours + minutes / 60) / 12) * 360;

        // Обновляем стрелки
        const secondHand = document.getElementById('secondHand');
        const minuteHand = document.getElementById('minuteHand');
        const hourHand = document.getElementById('hourHand');

        if (secondHand) secondHand.style.transform = `rotate(${secondDegrees}deg)`;
        if (minuteHand) minuteHand.style.transform = `rotate(${minuteDegrees}deg)`;
        if (hourHand) hourHand.style.transform = `rotate(${hourDegrees}deg)`;
    }

    function updateClockFromServer() {
        setClockFromServer();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', setClockFromServer);
    } else {
        setClockFromServer();
    }