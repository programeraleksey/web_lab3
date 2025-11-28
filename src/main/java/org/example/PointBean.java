package org.example;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class PointBean implements Serializable {

    private Double x;
    private Double y;
    private Double r = 3.5;
    private int slideR = 6;

    private boolean submitted;

    public List<Double> getPossibleX() {
        return Arrays.asList(-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5);
    }

    public void submit() {
        submitted = true;
    }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }

    public void setR(Double r) {
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

    public boolean isSubmitted() { return submitted; }
    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
}
