package org.example.ejb;

import jakarta.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class PointServiceBean {

    public boolean checkPoint(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (r == null || r.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        boolean inRectangle = (x.compareTo(r.negate()) >= 0) &&
                (x.compareTo(BigDecimal.ZERO) <= 0) &&
                (y.compareTo(r.negate()) >= 0) &&
                (y.compareTo(BigDecimal.ZERO) <= 0);


        boolean inTriangle = (x.compareTo(r.negate()) >= 0) &&
                (x.compareTo(BigDecimal.ZERO) <= 0) &&
                (y.compareTo(BigDecimal.ZERO) >= 0) &&
                (y.multiply(BigDecimal.valueOf(2)).compareTo(x.add(r)) <= 0);


        boolean inCircle = (x.compareTo(BigDecimal.ZERO) >= 0) &&
                (y.compareTo(BigDecimal.ZERO) >= 0) &&
                (x.pow(2).add(y.pow(2)).compareTo(r.pow(2)) <= 0);

        return inRectangle || inTriangle || inCircle;
    }
}