package com.example.praktikalistproducts.factory;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.dao.impl.MySQLProductDAO;
import com.example.praktikalistproducts.dao.impl.MemoryProductDAO;
import com.example.praktikalistproducts.dao.impl.PostgresProductDAO;

/**
 * Фабрика для создания DAO объектов доступа к данным продуктов.
 * Поддерживает различные типы хранилищ данных.
 */
public class ProductFactory {
    /** Константа для выбора PostgreSQL базы данных */
    public static final String DB = "DB";

    /** Константа для выбора MySQL базы данных */
    public static final String MYSQL = "MYSQL";

    /** Константа для выбора хранения в оперативной памяти */
    public static final String RAM = "RAM";

    /**
     * Создает экземпляр ProductDAO в зависимости от указанного типа хранилища.
     *
     * @param type тип хранилища данных (DB, MYSQL или RAM)
     * @return реализацию ProductDAO для указанного типа хранилища
     * @throws IllegalArgumentException если передан неизвестный тип хранилища
     * @throws RuntimeException если произошла ошибка при создании DAO
     */
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