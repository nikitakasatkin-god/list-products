package com.example.praktikalistproducts;

public class Product {
    private int id; // Новое поле
    private String name;
    private int count;
    private Tag tag;
    private String status;

    public Product(int id, String name, int count, Tag tag, String status) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.tag = tag;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}