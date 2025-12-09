package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.*;
@Entity
@Table(name = "results")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //автоматическая генерация с увеличением
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, precision = 20, scale = 10)
    private BigDecimal x;

    @Column(nullable = false, precision = 20, scale = 10)
    private BigDecimal y;

    @Column(nullable = false, precision = 20, scale = 10)
    private BigDecimal r;

    @Column(nullable = false)
    private boolean isHit;

    @Column(nullable = false)
    private LocalDateTime checkTime;

    @Column(nullable = false)
    private long executionTimeMs;

    public ResultEntity() {
    }

    public ResultEntity(String username, BigDecimal x, BigDecimal y, BigDecimal r, boolean isHit, long executionTimeMs) {
        this.id = null;
        this.username = username;
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        this.checkTime = LocalDateTime.now();
        this.executionTimeMs = executionTimeMs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public BigDecimal getX() { return x; }
    public void setX(BigDecimal x) { this.x = x; }
    public BigDecimal getY() { return y; }
    public void setY(BigDecimal y) { this.y = y; }
    public BigDecimal getR() { return r; }
    public void setR(BigDecimal r) { this.r = r; }
    public boolean isHit() { return isHit; }
    public void setHit(boolean hit) { isHit = hit; }
    public LocalDateTime getCheckTime() { return checkTime; }
    public void setCheckTime(LocalDateTime checkTime) { this.checkTime = checkTime; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; }
    public String getFormattedTime() {
        return checkTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}