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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/controller/areacheckpoint")
public class AreaCheckPointServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        resp.setHeader("Access-Control-Expose-Headers", "hit, X-User-Id, Authorization");


        try {
            BigDecimal x = new BigDecimal(req.getParameter("x")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal y = new BigDecimal(req.getParameter("y")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal r = new BigDecimal(req.getParameter("r")).setScale(2, RoundingMode.HALF_UP);

            if (x.compareTo(BigDecimal.valueOf(-5)) < 0 || x.compareTo(BigDecimal.valueOf(5)) > 0 ||
                    y.compareTo(BigDecimal.valueOf(-5)) < 0 || y.compareTo(BigDecimal.valueOf(5)) > 0 ||
                    r.compareTo(BigDecimal.ONE) < 0 || r.compareTo(BigDecimal.valueOf(5)) > 0) {
                throw new IllegalArgumentException("Значения вне допустимого диапазона");
            }

            boolean hit = AreaChecker.checkHit(x, y, r);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(formatter);

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

            resp.sendRedirect(req.getContextPath() + "/result.jsp");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Content-Type", "text/plain;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("Wrong coordinates!");
            out.flush();
        }
    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setHeader("Access-Control-Expose-Headers", "hit, X-User-Id");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}