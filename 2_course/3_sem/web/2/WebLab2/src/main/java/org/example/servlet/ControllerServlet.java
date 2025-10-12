package org.example.servlet;
//Это единая точка входа для всех запросов
//Если пришли x, y, r → передать в AreaCheckServlet

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.nanoTime();
        req.setAttribute("startTime", startTime);

        String x = req.getParameter("x");
        String y = req.getParameter("y");
        String r = req.getParameter("r");
        String graph = req.getParameter("graph");


        // Если все параметры переданы — отправляем на проверку
        if (x != null && y != null && r != null && graph != null &&
                !x.trim().isEmpty() && !y.trim().isEmpty() && !r.trim().isEmpty() && !graph.trim().isEmpty()) {
            if (graph.equals("/areacheck")){
                req.getRequestDispatcher("/controller/areacheck").forward(req, resp);
            }
            else if (graph.equals("/areacheckpoint")){
                req.getRequestDispatcher("/controller/areacheckpoint").forward(req, resp);
            }
            else if (graph.equals("/areacheckjs")){
                req.getRequestDispatcher("/controller/areacheckjs").forward(req, resp);
            }

        } else {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}