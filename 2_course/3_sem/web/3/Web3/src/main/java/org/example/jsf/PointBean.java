package org.example.jsf;

import jakarta.enterprise.context.SessionScoped;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.Point;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.example.jsf.CheckBean;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("pointBean")
@SessionScoped
public class PointBean implements Serializable {
    private BigDecimal x = new BigDecimal("1");
    private BigDecimal y = new BigDecimal("1");
    private BigDecimal r = new BigDecimal("1");

    @Inject
    private ControllerBean controllerBean;

    public PointBean() {
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
    public boolean checkRange(BigDecimal x, BigDecimal y, BigDecimal r){
        if (x == null || y == null || r == null) {
            return false;
        }

        if (x.compareTo(new BigDecimal("-5")) < 0 || x.compareTo(new BigDecimal("5")) > 0) {
            return false;
        }

        if (y.compareTo(new BigDecimal("-5")) < 0 || y.compareTo(new BigDecimal("5")) > 0) {
            return false;
        }

        if (r.compareTo(new BigDecimal("1")) < 0 || r.compareTo(new BigDecimal("4")) > 0) {
            return false;
        }
        return true;
    }

    public void submit() {

        BigDecimal x = getX();
        BigDecimal y = getY();
        BigDecimal r = getR();

        if (x.scale() > 0 && x.stripTrailingZeros().scale() > 0) {
            return;
        }
        if (!checkRange(x, y, r)){
            return;
        }

        send(x, y, r);
    }

    public void send(BigDecimal x, BigDecimal y, BigDecimal r) {
        long start = System.nanoTime();

        Point p = new Point();

        p.setX(x);
        p.setY(y);
        p.setR(r);

        p.setDate(LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        p.setCheck(CheckBean.checkHit(x, y, r));

        p.setDuration((System.nanoTime() - start)/1000);

        controllerBean.addPoint(p);
    }
    public void submitWithoutValidation() {
        BigDecimal x = getX();
        BigDecimal y = getY();
        BigDecimal r = getR();
        if (!checkRange(x, y, r)){
            return;
        }
        send(x, y, r);
    }
}