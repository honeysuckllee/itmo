package org.example;

import java.math.BigDecimal;

public class Hit {
    public boolean hit(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal halfR = r.divide(BigDecimal.valueOf(2));
        BigDecimal negR = r.negate();
        BigDecimal negHalfR = halfR.negate();

        /*if (x <= 0 && y >= 0) {
            return x >= -r && x <= -r / 2 && y <= r;
        }*/
        if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0){
            boolean xInSquare = x.compareTo(negR) >= 0;
            boolean yInSquare = y.compareTo(r) <= 0;
            return xInSquare && yInSquare;
        }

        /*
        if (x <= 0 && y <= 0) {
            return x >= -r / 2 && y >= -r && y <= x + r / 2;
        }*/
        if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0){
            BigDecimal xPlusHalfR = x.add(halfR);
            boolean xInTriagle = x.compareTo(negHalfR) >= 0;
            boolean yInTriagle = y.compareTo(negR) >= 0 && y.compareTo(xPlusHalfR) <= 0;
            return xInTriagle && yInTriagle;
        }

        /*
        if (x >= 0 && y <= 0) {
            return (x * x + y * y) <= (r * r);
        } */
        if (x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0){
            BigDecimal xSquared = x.multiply(x);           // x * x
            BigDecimal ySquared = y.multiply(y);           // y * y
            BigDecimal sumOfSquares = xSquared.add(ySquared); // x² + y²

            BigDecimal rSquared = r.multiply(r);           // r * r

            return sumOfSquares.compareTo(rSquared) <= 0;
        }

        return false;





    }
}