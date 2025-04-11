package com.example.praktikalistproducts.dao;

import com.example.praktikalistproducts.dao.impl.MySQLProductDAO;
import com.example.praktikalistproducts.dao.impl.MemoryProductDAO;
import com.example.praktikalistproducts.dao.impl.PostgresProductDAO;

public class ProductFactory {
    public static final String DB = "DB";
    public static final String MYSQL = "MYSQL";
    public static final String RAM = "RAM";

    public static ProductDAO createProductDAO(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Тип источника данных не может быть пустым");
        }

        try {
            switch (type) {
                case DB:
                    return new PostgresProductDAO();
                case MYSQL:
                    return new MySQLProductDAO();
                case RAM:
                    return new MemoryProductDAO();
                default:
                    throw new IllegalArgumentException("Неизвестный тип источника данных: " + type);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании DAO для типа " + type);
            e.printStackTrace();
            throw e;
        }
    }
}