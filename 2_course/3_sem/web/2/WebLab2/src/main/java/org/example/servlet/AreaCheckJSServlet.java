package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.AreaChecker;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/controller/areacheckjs")

public class AreaCheckJSServlet extends HttpServlet {
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
            resp.getWriter().write(String.valueOf(hit));

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
