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

@WebServlet("/controller/areacheck")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = formatter.format(new Date());

            long startTime = (long) req.getAttribute("startTime");
            long executionTimeNanos = System.nanoTime() - startTime;
            long executionTimeMicros = TimeUnit.NANOSECONDS.toMicros(executionTimeNanos);

            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked") //чтобы не ругался на приведения типов
            List<PointResult> results = (List<PointResult>) session.getAttribute("results");

            if (results == null) {
                results = new ArrayList<>();
                session.setAttribute("results", results);
            }

            results.add(new PointResult(x, y, r, hit, currentTime, executionTimeMicros));

           // req.getRequestDispatcher("/result.jsp").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/result.jsp");

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}