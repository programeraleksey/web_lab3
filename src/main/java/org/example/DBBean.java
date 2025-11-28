package org.example;

import org.example.model.HitResult;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named("dbBean")
@SessionScoped
public class DBBean implements Serializable {

    private double x;
    private double y;
    private double r = 3.5;
    private int slideR = 6;

    private List<HitResult> results;

    @PersistenceContext(unitName = "LabPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        loadResults();
    }

    public List<Double> getPossibleX() {
        return Arrays.asList(-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5);
    }

    @Transactional
    public String process() {
        boolean hit = isHit(x, y, r);

        HitResult entity = new HitResult(x, y, r, hit, LocalDateTime.now());
        em.persist(entity);

        loadResults();

        return null;
    }

    private boolean isHit(double x, double y, double r) {
        if (x < 0 && y > 0) {return false;}
        if (x <= 0 && y <= 0) {return x >= -r/2 && y >= -r;}
        if (x >= 0 && y <= 0) {return x - y <= r/2;}
        if (x >= 0 && y >= 0) {return x*x+y*y <= r*r/4;}
        return false;
    }

    private void loadResults() {
        results = em.createQuery("select r from HitResult r order by r.createdAt desc",
                HitResult.class).getResultList();
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

    public List<HitResult> getResults() { return results; }

    public String getPointsJson() {
        if (getResults().isEmpty()) {
            return "[]";
        }
        String jsonArray = results.stream()
                .map(r -> String.format(
                        "{\"x\":%s,\"y\":%s,\"r\":%s,\"hit\":%s}",
                        r.getX(), r.getY(), r.getR(), r.isHit()))
                .collect(Collectors.joining(","));

        return "[" + jsonArray + "]";
    }
}
