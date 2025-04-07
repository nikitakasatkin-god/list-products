package com.example.praktikalistproducts.model;

import javafx.beans.property.*;

public class Product {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty count = new SimpleIntegerProperty();
    private final ObjectProperty<Tag> tag = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    public Product(int id, String name, int count, Tag tag, String status) {
        this.id.set(id);
        this.name.set(name);
        this.count.set(count);
        this.tag.set(tag);
        this.status.set(status);
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public IntegerProperty countProperty() { return count; }
    public ObjectProperty<Tag> tagProperty() { return tag; }
    public StringProperty statusProperty() { return status; }

    // Regular getters and setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public int getCount() { return count.get(); }
    public void setCount(int count) { this.count.set(count); }

    public Tag getTag() { return tag.get(); }
    public void setTag(Tag tag) { this.tag.set(tag); }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
}