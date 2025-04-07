package com.example.praktikalistproducts.dao;

import com.example.praktikalistproducts.dao.impl.*;

public class ProductFactory {
    public static final String DB = "база данных";
    public static final String FILE = "файл";
    public static final String RAM = "временно";

    public static ProductDAO createProductDAO(String type) {
        if (type.equalsIgnoreCase(DB)) {
            return new PostgresProductDAO();
        } else if (type.equalsIgnoreCase(FILE)) {
            return new FileProductDAO("products.txt"); // Передаем имя файла
        } else if (type.equalsIgnoreCase(RAM)) {
            return new MemoryProductDAO(10); // С начальной емкостью 10
        } else {
            throw new IllegalArgumentException("Неверный тип хранилища!");
        }
    }
}