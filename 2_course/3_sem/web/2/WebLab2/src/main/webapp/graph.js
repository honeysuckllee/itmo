function showFlashMessage(message) {
    const flash = document.getElementById('flash-message');
    flash.textContent = message;
    flash.classList.add('show');
    setTimeout(() => flash.classList.remove('show'), 3000);
}
export function svgMousePoint(){
    const toSVGPoint = (svg, x, y) => { //перевод глобавльных координат экрана в локальные координаты svg
        let p = new DOMPoint(x, y);
        return p.matrixTransform(svg.getScreenCTM().inverse());
    };
    const svg = document.querySelector("svg")

    svg.addEventListener("click", e => {
        let p = toSVGPoint(svg, e.clientX, e.clientY);
        const cx = p.x;
        const cy = p.y;
        const R = parseInt(document.querySelector('select[name="r"]').value)
        if (Number.isNaN(R)){
            showFlashMessage("Выберите R!");
            return;
        }

        const chartStep = 176 //пиксельное расстояние от центра SVG до максимального R
        const globalStep = chartStep / R
        const x = (cx - 220) / globalStep
        const y = (220 - cy) / globalStep

        const formData = new URLSearchParams();
        formData.append('x', "" + x);
        formData.append('y', "" + y);
        formData.append('r', "" + R);
        formData.append("path", "/areacheckpoint");

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
                    showFlashMessage(`Ошибка запроса: ${error.message}`);
                });
    });
}

window.addEventListener('DOMContentLoaded', svgMousePoint);