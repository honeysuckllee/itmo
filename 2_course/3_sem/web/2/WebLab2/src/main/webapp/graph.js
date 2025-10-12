function showFlashMessage(message) {
    const flash = document.getElementById('flash-message');
    flash.textContent = message;
    flash.classList.add('show');
    setTimeout(() => flash.classList.remove('show'), 3000);
}
export function svgMousePoint(){
    const toSVGPoint = (svg, x, y) => {
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
        localStorage.setItem("r", "" + R);
        const chartStep = 176
        const globalStep = chartStep / R
        const x = (cx - 220) / globalStep
        const y = (220 - cy) / globalStep
        const data = {
            x: x,
            y: y,
            R: R
        }
        console.log(cx, cy,x, y, R);
        const formData = new URLSearchParams();
        formData.append('x', "" + x);
        formData.append('y', "" + y);
        formData.append('r', "" + R);
        formData.append("graph", "/areacheckpoint");

            fetch(`controller`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                },
                //redirect: "follow",
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Сервер вернул ошибку: ${response.status}`);
                    }
                    if (response.ok) {
                        localStorage.setItem("cx", cx);
                        localStorage.setItem("cy", cy);
                        console.log(cx, cy, "->");
                        let h = response.headers.get("hit");
                        localStorage.setItem("hit", response.headers.get("hit"));
                        window.location.href = response.url;
                    }
                    return response.json();
                })

                .then(data => {
                    if (data.success) {
                        // Сохраняем в localStorage
                        localStorage.setItem("lastResult", JSON.stringify(data));
                        console.log(data.hit);
                        window.location.href = "result.jsp";
                    }
                })
                .catch(error => {
                    console.error("Ошибка:", error);
                    showFlashMessage(`Ошибка запроса: ${error.message}`);
                });
    });
}

window.addEventListener('DOMContentLoaded', svgMousePoint);