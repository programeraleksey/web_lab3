package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "figure")
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public FigureType type;

    @Column(name = "x_coord", nullable = false)
    public double xCoord;

    @Column(name = "y_coord", nullable = false)
    public double yCoord;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FigureType getType() { return type; }
    public void setType(FigureType type) { this.type = type; }

    public double getXCoord() { return xCoord; }
    public void setXCoord(double xCoord) { this.xCoord = xCoord; }

    public double getYCoord() { return yCoord; }
    public void setYCoord(double yCoord) { this.yCoord = yCoord; }

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id + ","
                + "\"type\":\"" + type.name().toLowerCase() + "\","
                + "\"x\":" + xCoord + ","
                + "\"y\":" + yCoord
                + "}";
    }
}
