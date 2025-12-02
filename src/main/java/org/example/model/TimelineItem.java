package org.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "timeline_item")
public class TimelineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graph_id", nullable = false)
    private Graph graph;

    private double r;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Graph getGraph() { return graph; }
    public void setGraph(Graph graph) { this.graph = graph; }

    public double getR() { return r; }
    public void setR(double r) { this.r = r; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id + ","
                + "\"r\":" + r + ","
                + "\"startTime\":\"" + startTime + "\","
                + "\"endTime\":" + (endTime != null ? "\"" + endTime + "\"" : "null") + ","
                + "\"graph\":" + (graph != null ? graph.toString() : "null")
                + "}";
    }
}