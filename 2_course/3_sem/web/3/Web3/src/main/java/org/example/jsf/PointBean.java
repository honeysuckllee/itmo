package org.example.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.Point;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.example.jsf.CheckBean;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("pointBean")
@SessionScoped
public class PointBean implements Serializable {
    private BigDecimal x = null;
    private BigDecimal y = new BigDecimal("1");
    private BigDecimal r = new BigDecimal("1");
    private List<Point> results;

    @Inject
    private ControllerBean controllerBean;

    public PointBean() {
    }
    public List<Point> getResults() {
        return results;
    }

    public void setResults(List<Point> results) {
        this.results = results;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public void submit() {
        send(getX(), getY(), getR());
    }

    public void send(BigDecimal x, BigDecimal y, BigDecimal r) {
        long start = System.nanoTime();
        LocalDateTime localDateTime = LocalDateTime.now();

        if (x == null || y == null || r == null) {
            return;
        }

        Point p = new Point();

        p.setX(x);
        p.setY(y);
        p.setR(r);

        p.setDate(localDateTime);
        p.setCheck(CheckBean.checkHit(x, y, r));

        p.setDuration(System.nanoTime() - start);

        controllerBean.addPoint(p);
    }
}