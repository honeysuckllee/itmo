package org.example.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("pointBean")
@SessionScoped
public class PointBean implements Serializable {
    private Double x = 0.0;
    private Double y = 0.0;
    private Double r = 1.0;
    private List<PointResult> results = new ArrayList<>();

    public static class PointResult {
        private Double x;
        private Double y;
        private Double r;
        private Boolean hit;
        private LocalDateTime timestamp;
        private Long executionTime;

        public PointResult(Double x, Double y, Double r, Boolean hit, Long executionTime) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.hit = hit;
            this.timestamp = LocalDateTime.now();
            this.executionTime = executionTime;
        }

        // Геттеры
        public Double getX() { return x; }
        public Double getY() { return y; }
        public Double getR() { return r; }
        public Boolean getHit() { return hit; }
        public String getTimestamp() {
            return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        public Long getExecutionTime() { return executionTime; }
    }

    public String checkPoint() {
        long startTime = System.currentTimeMillis();

        // Логика проверки попадания
        boolean hit = checkHit(x, y, r);

        long executionTime = System.currentTimeMillis() - startTime;

        PointResult result = new PointResult(x, y, r, hit, executionTime);
        results.add(0, result); // Добавляем в начало списка

        return null;
    }

    private boolean checkHit(Double x, Double y, Double r) {
        // Проверка попадания в первую четверть (прямоугольник)
        if (x >= 0 && y >= 0 && x <= r/2 && y <= r) {
            return true;
        }
        // Проверка попадания во вторую четверть (треугольник)
        if (x <= 0 && y >= 0 && y <= x + r) {
            return true;
        }
        // Проверка попадания в четвертую четверть (окружность)
        if (x >= 0 && y <= 0 && (x*x + y*y) <= (r*r)) {
            return true;
        }
        return false;

        //return CheckBean.checkHit(x, y, r);
    }

    public void clearResults() {
        results.clear();
    }

    // Геттеры и сеттеры
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }

    public List<PointResult> getResults() { return results; }
}