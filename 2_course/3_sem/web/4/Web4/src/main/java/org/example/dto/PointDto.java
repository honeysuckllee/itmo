package org.example.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class PointDto {
    @NotNull(message = "X cannot be null")
    @DecimalMin(value = "-3.0", message = "X must be >= -3")
    @DecimalMax(value = "3.0", message = "X must be <= 3")
    private String x;
    @NotNull(message = "Y cannot be null")
    @DecimalMin(value = "-5.0", message = "Y  must be >= -5")
    @DecimalMax(value = "5.0", message = "Y  must be <= 5")
    private String y;
    @NotNull(message = "R cannot be null")
    @DecimalMin(value = "1.0", message = "R  must be >= 1")
    @DecimalMax(value = "3.0", message = "R  must be <= 3")
    private String r;

    public PointDto() {
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }
}