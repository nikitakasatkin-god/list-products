package com.example.praktikalistproducts;

import com.example.praktikalistproducts.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Класс для управления списком продуктов.
 * Предоставляет методы для работы с ObservableList продуктов.
 */
public class ListProduct {
    private ObservableList<Product> products;

    /**
     * Конструктор класса ListProduct.
     * Инициализирует ObservableList для хранения продуктов.
     */
    public ListProduct() {
        products = FXCollections.observableArrayList();
    }

    /**
     * Получение списка всех продуктов.
     * @return ObservableList содержащий все продукты
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    /**
     * Добавление нового продукта в список.
     * @param product продукт для добавления
     */
    public void addProduct(Product product) {
        products.add(product);
    }
}