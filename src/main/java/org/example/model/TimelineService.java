package org.example.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TimelineService {

    @PersistenceContext
    private EntityManager em;

    public List<TimelineItem> findAllWithGraphAndFigures() {
        return em.createQuery(
                "SELECT ti FROM TimelineItem ti " +
                        "JOIN FETCH ti.graph g " +
                        "LEFT JOIN FETCH g.firstQuarterFigure " +
                        "LEFT JOIN FETCH g.secondQuarterFigure " +
                        "LEFT JOIN FETCH g.thirdQuarterFigure " +
                        "LEFT JOIN FETCH g.fourthQuarterFigure",
                TimelineItem.class
        ).getResultList();
    }
}
