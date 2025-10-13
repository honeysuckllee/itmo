package org.example.servlet;
//Это единая точка входа для всех запросов

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
        String path = req.getParameter("path");

        if (x != null && y != null && r != null && path != null &&
                !x.trim().isEmpty() && !y.trim().isEmpty() && !r.trim().isEmpty() && !path.trim().isEmpty()) {
            if (path.equals("/areacheck")){
                req.getRequestDispatcher("/controller/areacheck").forward(req, resp);
            }
            else if (path.equals("/areacheckpoint")){
                req.getRequestDispatcher("/controller/areacheckpoint").forward(req, resp);
            }
            else if (path.equals("/areacheckjs")){
                req.getRequestDispatcher("/controller/areacheckjs").forward(req, resp);
            }
        }else
        if (path != null && path.equals("/cleartable")){
            req.getRequestDispatcher("/controller/cleartable").forward(req, resp);
        }
        else {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}