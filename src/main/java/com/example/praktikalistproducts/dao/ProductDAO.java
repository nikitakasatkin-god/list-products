package com.example.praktikalistproducts.dao;

import com.example.praktikalistproducts.model.Product;
import java.util.List;

/**
 * Интерфейс доступа к данным продуктов (Data Access Object).
 * Определяет стандартные CRUD-операции для работы с продуктами.
 */
public interface ProductDAO {
    /**
     * Получает список всех продуктов из хранилища.
     * @return список объектов Product
     */
    List<Product> getAllProducts();

    /**
     * Находит продукт по его идентификатору.
     * @param id идентификатор продукта
     * @return найденный объект Product или null, если продукт не найден
     */
    Product getProductById(int id);

    /**
     * Добавляет новый продукт в хранилище.
     * @param product объект Product для добавления
     * @throws IllegalArgumentException если продукт равен null или содержит некорректные данные
     */
    void addProduct(Product product);

    /**
     * Обновляет существующий продукт в хранилище.
     * @param product объект Product с обновленными данными
     * @throws IllegalArgumentException если продукт равен null или не существует в хранилище
     */
    void updateProduct(Product product);

    /**
     * Удаляет продукт из хранилища по идентификатору.
     * @param id идентификатор продукта для удаления
     * @throws IllegalArgumentException если продукт с указанным id не существует
     */
    void deleteProduct(int id);
}