package org.example;

import org.example.model.HitResult;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named("coordBean")
@SessionScoped
public class CoordBean implements Serializable {

    private double x;
    private double y;
    private double r = 3.5;
    private int slideR = 6;

    @Inject
    private DataBaseBean dataBaseBean;

    public List<Double> getPossibleX() {
        return Arrays.asList(-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5);
    }

    public String process() {
        boolean hit = isHit(x, y, r);

        dataBaseBean.addHit(x, y, r, hit);

        return null;
    }

    private boolean isHit(double x, double y, double r) {
        if (x < 0 && y > 0) { return false; }
        if (x <= 0 && y <= 0) { return x >= -r / 2 && y >= -r; }
        if (x >= 0 && y <= 0) { return x - y <= r / 2; }
        if (x >= 0 && y >= 0) { return x * x + y * y <= r * r / 4; }
        return false;
    }

    public double getX() { return x; }
    public void setX(double x) {
        this.x = Math.round(x * 100) / 100.0;
    }

    public double getY() { return y; }
    public void setY(double y) {
        this.y = Math.round(y * 100) / 100.0;
    }

    public double getR() { return r; }
    public void setR(double r) {
        this.r = r;
        this.slideR = (int) Math.round((r - 2.0) / 0.25);
    }

    public int getSlideR() {
        return slideR;
    }

    public void setSlideR(int slideR) {
        this.slideR = slideR;
        this.r = 2.0 + slideR * 0.25;
    }
}
