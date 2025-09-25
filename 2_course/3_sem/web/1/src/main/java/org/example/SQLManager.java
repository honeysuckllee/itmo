package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;


public class SQLManager {
    private static final Logger logger = Logger.getLogger(SQLManager.class.getName());
    private Connection connection;


    public SQLManager() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл db.properties не найден в ресурсах");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения db.properties", e);
        }

        String dbUrl = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Соединение с базой данных успешно установлено");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }


    public void insertHitResult(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit, double execTime, String curTime) {
        if (connection == null) {
            throw new IllegalStateException("Соединение с БД не установлено");
        }

        String sql = "INSERT INTO hit_result (x, y, r, hit, exec_time, cur_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, x);
            stmt.setBigDecimal(2, y);
            stmt.setBigDecimal(3, r);
            stmt.setBoolean(4, hit);
            stmt.setDouble(5, execTime);
            stmt.setString(6, curTime);
            stmt.executeUpdate();
            logger.info("Новая запись добавлена в БД: x=" + x + ", y=" + y + ", r=" + r + ", hit=" + hit);
        } catch (SQLException e) {
            logger.severe("Ошибка вставки записи: " + e.getMessage());
            throw new RuntimeException("Ошибка при вставке в БД", e);
        }
    }

    public void clearHitResult() {
        if (connection == null) {
            throw new IllegalStateException("Соединение с БД не установлено");
        }

        String sql = "TRUNCATE TABLE hit_result";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            logger.info("Таблица hitResult успешно очищена");
        } catch (SQLException e) {
            logger.severe("Ошибка очистки таблицы: " + e.getMessage());
            throw new RuntimeException("Ошибка при очистке таблицы", e);
        }
    }


    public Connection getConnection() {
        return connection;
    }
}
