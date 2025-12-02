package org.example.model;

public class CanvasItem {
    public String id;
    public String label;

    public CanvasItem(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
}