package org.example.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.math.BigDecimal;

import java.io.Serializable;
import java.math.RoundingMode;

@Named("checker")
@SessionScoped

public class CheckBean implements Serializable {

    public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x == null || y == null || r == null) {
            return false;
        }

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal halfR = r.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
        BigDecimal negHalfR = halfR.negate();

        if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            BigDecimal xSq = x.multiply(x);
            BigDecimal ySq = y.multiply(y);
            BigDecimal sumSq = xSq.add(ySq);
            BigDecimal rSq = r.multiply(r);
            if (sumSq.compareTo(rSq) <= 0) {
                return true;
            }
        }

        // === Вторая четверть: прямоугольник ===
        if (x.compareTo(zero) <= 0 &&
                y.compareTo(zero) >= 0 &&
                x.compareTo(negHalfR) >= 0 &&
                y.compareTo(halfR) <= 0) {
            return true;
        }

        // === Четвёртая четверть: треугольник ===
        if (x.compareTo(zero) >= 0 &&
                y.compareTo(zero) <= 0 &&
                x.compareTo(halfR) <= 0) {
            BigDecimal minY = x.subtract(halfR); // y >= x - r/2  <=>  y >= x - halfR
            if (y.compareTo(minY) >= 0) {
                return true;
            }
        }
        return false;
    }
}