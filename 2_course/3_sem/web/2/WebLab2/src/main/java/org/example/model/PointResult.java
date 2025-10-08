package org.example.model;
//Хранит один результат проверки: координаты точки, радиус, попала ли она в область, и когда это произошло.
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PointResult {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;
    private final boolean hit;
    private final String timestamp; // форматированная строка
    private final long executionTimeMicros; // время в микросекундах

    public PointResult(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit, String timestamp, long executionTimeMicros) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.timestamp = timestamp;
        this.executionTimeMicros = executionTimeMicros;
    }

    // Getters
    public BigDecimal getX() { return x; }
    public BigDecimal getY() { return y; }
    public BigDecimal getR() { return r; }
    public boolean isHit() { return hit; }
    public String getTimestamp() { return timestamp; }
    public long getExecutionTimeMicros() { return executionTimeMicros; }
}