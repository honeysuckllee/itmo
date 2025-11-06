package org.example.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named("resultBean")
@SessionScoped
public class ResultBean implements Serializable {

    private BigDecimal x;
    private String y; // будем парсить в BigDecimal
    private Integer r = 1;

    private List<CheckResult> results = new ArrayList<>();

    private static final List<Integer> X_VALUES = Arrays.asList(-3, -2, -1, 0, 1, 2, 3, 4, 5);

    @PostConstruct
    public void init() {
        if (results.isEmpty()) {
            // можно предзаполнить, если нужно
        }
    }

    public String checkPoint() {
        try {
            BigDecimal yVal = new BigDecimal(y.trim());
            boolean hit = checkHit(x, yVal, r);

            CheckResult result = new CheckResult(
                    x, yVal, r, hit,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                    (long) (Math.random() * 1000) // имитация времени выполнения
            );
            results.add(0, result); // добавляем в начало (как в оригинале)
        } catch (Exception e) {
            // можно добавить сообщение об ошибке через FacesContext
        }
        return null; // AJAX-обновление
    }

    public String clearResults() {
        results.clear();
        return null;
    }

    // Логика проверки попадания (пример — замените на свою)
    private boolean checkHit(BigDecimal x, BigDecimal y, Integer r) {
        if (x == null || y == null || r == null) return false;
        // Пример: область — прямоугольник + треугольник + сектор
        double xd = x.doubleValue();
        double yd = y.doubleValue();
        double rd = r.doubleValue();

        boolean inRect = (xd >= -rd && xd <= 0 && yd >= -rd && yd <= 0);
        boolean inTri = (xd >= 0 && yd >= 0 && yd <= -xd + rd);
        boolean inSect = (xd <= 0 && yd >= 0 && xd * xd + yd * yd <= rd * rd);

        return inRect || inTri || inSect;
    }

    public BigDecimal getX() { return x; }
    public void setX(BigDecimal x) { this.x = x; }

    public String getY() { return y; }
    public void setY(String y) { this.y = y; }

    public Integer getR() { return r; }
    public void setR(Integer r) { this.r = r; }

    public List<CheckResult> getResults() { return results; }
    public List<Integer> getXValues() { return X_VALUES; }

    // Вложенный класс для результата
    public static class CheckResult implements Serializable {
        public final BigDecimal x, y;
        public final Integer r;
        public final boolean hit;
        public final String timestamp;
        public final long executionTimeMicros;

        public CheckResult(BigDecimal x, BigDecimal y, Integer r, boolean hit, String timestamp, long executionTimeMicros) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.hit = hit;
            this.timestamp = timestamp;
            this.executionTimeMicros = executionTimeMicros;
        }
    }
}
