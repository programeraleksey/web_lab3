package org.example;

import org.example.model.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;


@Named
@ViewScoped
public class TimelineBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TimelineService timelineService;

    private TimelineModel<TimelineItem, ?> model;

    @PostConstruct
    public void init() {
        model = new TimelineModel<>();

        for (TimelineItem event : timelineService.findAllWithGraphAndFigures()) {
            model.add(TimelineEvent.<TimelineItem>builder()
                    .data(event)
                    .startDate(event.getStartTime())
                    .build());
        }
    }


    public TimelineModel<TimelineItem, ?> getModel() {
        return model;
    }

    public String toJsonArray() {
        List<TimelineItem> items = timelineService.findAllWithGraphAndFigures();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
