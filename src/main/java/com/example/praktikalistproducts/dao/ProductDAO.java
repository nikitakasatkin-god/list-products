package com.example.praktikalistproducts.dao;

import com.example.praktikalistproducts.model.Product;
import java.util.List;

public interface ProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int id);
}