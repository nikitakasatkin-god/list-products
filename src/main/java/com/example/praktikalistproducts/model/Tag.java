package com.example.praktikalistproducts.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tag {
    private final int id;
    private final StringProperty tag = new SimpleStringProperty();

    public Tag(int id, String tag) {
        this.id = id;
        this.tag.set(tag);
    }

    public int getId() { return id; }

    public String getTag() { return tag.get(); }
    public void setTag(String tag) { this.tag.set(tag); }

    public StringProperty tagProperty() { return tag; }
}