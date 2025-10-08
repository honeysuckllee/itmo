package org.example.model;
//Содержит алгоритм проверки попадания точки в область

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AreaChecker {

    public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x == null || y == null || r == null) {
            return false;
        }

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal halfR = r.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
        BigDecimal negR = r.negate();

        // 1. Четверть круга (III четверть): x ≤ 0, y ≤ 0, x² + y² ≤ R²
        if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            BigDecimal xSq = x.multiply(x);
            BigDecimal ySq = y.multiply(y);
            BigDecimal sumSq = xSq.add(ySq);
            BigDecimal rSq = r.multiply(r);
            if (sumSq.compareTo(rSq) <= 0) {
                return true;
            }
        }

        // 2. Прямоугольник (IV четверть): 0 ≤ x ≤ R/2, -R ≤ y ≤ 0
        if (x.compareTo(zero) >= 0 && x.compareTo(halfR) <= 0 &&
                y.compareTo(negR) >= 0 && y.compareTo(zero) <= 0) {
            return true;
        }

        // 3. Треугольник (I четверть): 0 ≤ x ≤ R/2, 0 ≤ y ≤ R/2 - x
        if (x.compareTo(zero) >= 0 && x.compareTo(halfR) <= 0 &&
                y.compareTo(zero) >= 0) {
            BigDecimal maxY = halfR.subtract(x);
            if (y.compareTo(maxY) <= 0) {
                return true;
            }
        }

        return false;
    }
}
