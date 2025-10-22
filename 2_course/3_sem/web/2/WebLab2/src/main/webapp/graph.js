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
                .then(async response => {
                    if (!response.ok) {
                        const errorText = await response.text();
                        showFlashMessage('Ошибка: ' + errorText);
                        return;
                    }
                    if (response.ok) {
                        window.location.href = "result.jsp";
                    }
                })
                .catch(error => {
                    showFlashMessage(`Ошибка запроса: ${error.message}`);
                });
    });


    let currentX = 0;
    let currentY = 0;
    let cursorCircle = null;

    function updateCursor(r) {
        const scale = 176 / r;
        const cx = 220 + currentX * scale;
        const cy = 220 - currentY * scale;

        if (!cursorCircle) {
            cursorCircle = document.createElementNS('http://www.w3.org/2000/svg', 'circle' );
            cursorCircle.setAttribute('class', 'cursor')
            cursorCircle.setAttribute('r', '4');
            svg.appendChild(cursorCircle);
        }
        cursorCircle.setAttribute('cx', cx);
        cursorCircle.setAttribute('cy', cy);
    }
    function removeCursor() {
        if (cursorCircle) {
            cursorCircle.remove();
            cursorCircle = null;
        }
    }

    svg.addEventListener('keydown', function(e) {
        const rSelect = document.querySelector('select[name="r"]');
        const R = rSelect ? parseInt(rSelect.value) : NaN;
        if (isNaN(R) || R <= 0) {
            if (e.key === 'Enter') {
                showFlashMessage("Выберите R!");
            }
            return;
        }

        const STEP = 0.25;
        let moved = false;
        switch (e.key) {
            case 'ArrowLeft':  currentX = Math.max(-R, currentX - STEP); moved = true;e.preventDefault(); break;
            case 'ArrowRight': currentX = Math.min(R, currentX + STEP); moved = true;e.preventDefault(); break;
            case 'ArrowUp':    currentY = Math.min(R, currentY + STEP); moved = true;e.preventDefault(); break;
            case 'ArrowDown':  currentY = Math.max(-R, currentY - STEP); moved = true;e.preventDefault(); break;
            case 'Enter':
                e.preventDefault();

                const formData = new URLSearchParams();
                formData.append('x', "" + currentX);
                formData.append('y', "" + currentY);
                formData.append('r', "" + R);
                formData.append("path", "/areacheckpoint");

                fetch(`controller`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    },
                    body: formData
                })
                    .then(async response => {
                        if (!response.ok) {
                            const errorText = await response.text();
                            showFlashMessage('Ошибка: ' + errorText);
                            svg.focus();
                            return;
                        }
                        if (response.ok) {
                            window.location.href = "result.jsp";
                        }
                    })
                    .catch(error => {
                        showFlashMessage(`Ошибка запроса: ${error.message}`);
                        svg.focus();
                    });
                return;
        }

        if (moved) {
            updateCursor(R);
        }
    });
    svg.addEventListener('blur', removeCursor);
}

function handleEscapeFromGraph(e) {
    if (e.key === 'Escape') {
        const graph = document.getElementById('graph');
        const rSelect = document.getElementById('r');

        if (graph && document.activeElement === graph && rSelect) {
            rSelect.focus();
            e.preventDefault();
        }
    }
}

window.addEventListener('DOMContentLoaded', svgMousePoint);
window.addEventListener('keydown', handleEscapeFromGraph);