package org.example;

import org.example.model.HitResult;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class DataBaseBean implements Serializable {

    private volatile List<HitResult> results;

    @PersistenceContext(unitName = "LabPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        reload();
    }

    @Transactional
    public void addHit(double x, double y, double r, boolean hit) {
        HitResult entity = new HitResult(x, y, r, hit, LocalDateTime.now());
        em.persist(entity);
        reload();
    }

    public void reload() {
        results = em.createQuery(
                "select r from HitResult r order by r.createdAt desc",
                HitResult.class
        ).getResultList();
    }

    public List<HitResult> getResults() {
        return results;
    }

    public String getPointsJson() {
        List<HitResult> local = results;
        if (local == null || local.isEmpty()) {
            return "[]";
        }
        String jsonArray = local.stream()
                .map(r -> String.format(
                        "{\"x\":%s,\"y\":%s,\"r\":%s,\"hit\":%s}",
                        r.getX(), r.getY(), r.getR(), r.isHit()))
                .collect(Collectors.joining(","));

        return "[" + jsonArray + "]";
    }
}
