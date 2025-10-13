package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.PointResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/controller/cleartable")
public class ClearTableServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            List<PointResult> results = (List<PointResult>) session.getAttribute("results");

            if (results != null) {
                results = new ArrayList<>();
                session.setAttribute("results", results);
            }
            resp.getWriter().write("Таблица очищена");
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка: " + e.getMessage());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
