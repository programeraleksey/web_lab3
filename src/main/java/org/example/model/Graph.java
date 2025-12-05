package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "graphs")
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_quarter_figure")
    public Figure firstQuarterFigure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_quarter_figure")
    public Figure secondQuarterFigure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_quarter_figure")
    public Figure thirdQuarterFigure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fourth_quarter_figure")
    public Figure fourthQuarterFigure;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Figure getFirstQuarterFigure() { return firstQuarterFigure; }
    public void setFirstQuarterFigure(Figure firstQuarterFigure) { this.firstQuarterFigure = firstQuarterFigure; }

    public Figure getSecondQuarterFigure() { return secondQuarterFigure; }
    public void setSecondQuarterFigure(Figure secondQuarterFigure) { this.secondQuarterFigure = secondQuarterFigure; }

    public Figure getThirdQuarterFigure() { return thirdQuarterFigure; }
    public void setThirdQuarterFigure(Figure thirdQuarterFigure) { this.thirdQuarterFigure = thirdQuarterFigure; }

    public Figure getFourthQuarterFigure() { return fourthQuarterFigure; }
    public void setFourthQuarterFigure(Figure fourthQuarterFigure) { this.fourthQuarterFigure = fourthQuarterFigure; }

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id + ","
                + "\"firstQuarterFigure\":" + (firstQuarterFigure != null ? firstQuarterFigure.toString() : "null") + ","
                + "\"secondQuarterFigure\":" + (secondQuarterFigure != null ? secondQuarterFigure.toString() : "null") + ","
                + "\"thirdQuarterFigure\":" + (thirdQuarterFigure != null ? thirdQuarterFigure.toString() : "null") + ","
                + "\"fourthQuarterFigure\":" + (fourthQuarterFigure != null ? fourthQuarterFigure.toString() : "null")
                + "}";
    }

}
