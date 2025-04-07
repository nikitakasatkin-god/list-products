package com.example.praktikalistproducts;

import com.example.praktikalistproducts.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListProduct {
    private ObservableList<Product> products;

    public ListProduct() {
        products = FXCollections.observableArrayList();
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}