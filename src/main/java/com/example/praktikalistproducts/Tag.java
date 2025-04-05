package com.example.praktikalistproducts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tag {
    private int id;
    private String tag;
    private StringProperty tagProperty;

    public Tag(int id, String tag) {
        this.id = id;
        this.tag = tag;
        this.tagProperty = new SimpleStringProperty(tag);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
        this.tagProperty.set(tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringProperty tagProperty() {
        return tagProperty;
    }
}