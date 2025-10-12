package org.example.servlet;
//Обрабатывает конкретный тип запроса — когда пришли координаты и радиус.
import org.example.model.AreaChecker;
import org.example.model.PointResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            // === Парсинг параметров ===
            BigDecimal x = new BigDecimal(req.getParameter("x")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal y = new BigDecimal(req.getParameter("y")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal r = new BigDecimal(req.getParameter("r")).setScale(2, RoundingMode.HALF_UP);

            // === Валидация диапазонов ===
            if (x.compareTo(BigDecimal.valueOf(-5)) < 0 || x.compareTo(BigDecimal.valueOf(5)) > 0 ||
                    y.compareTo(BigDecimal.valueOf(-5)) < 0 || y.compareTo(BigDecimal.valueOf(5)) > 0 ||
                    r.compareTo(BigDecimal.ONE) < 0 || r.compareTo(BigDecimal.valueOf(5)) > 0) {
                throw new IllegalArgumentException("Значения вне допустимого диапазона");
            }

            // === Проверка попадания ===
            boolean hit = AreaChecker.checkHit(x, y, r);

            // === Форматирование текущего времени ===
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = formatter.format(new Date());

            long startTime = (long) req.getAttribute("startTime");
            long executionTimeNanos = System.nanoTime() - startTime;
            long executionTimeMicros = TimeUnit.NANOSECONDS.toMicros(executionTimeNanos);

            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            List<PointResult> results = (List<PointResult>) session.getAttribute("results");

            if (results == null) {
                results = new ArrayList<>();
                session.setAttribute("results", results);
            }

            // Добавляем новый результат
            results.add(new PointResult(x, y, r, hit, currentTime, executionTimeMicros));

            // === Передача данных в JSP ===
            req.setAttribute("x", x);
            req.setAttribute("y", y);
            req.setAttribute("r", r);
            req.setAttribute("hit", hit);
            req.setAttribute("currentTime", currentTime);
            req.setAttribute("executionTimeMicros", executionTimeMicros);

            req.getRequestDispatcher("/result.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}