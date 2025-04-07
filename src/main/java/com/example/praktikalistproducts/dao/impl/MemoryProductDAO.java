package com.example.praktikalistproducts.dao.impl;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryProductDAO implements ProductDAO {
    private final List<Product> products;
    private final Random random = new Random();

    public MemoryProductDAO(int initialCapacity) {
        this.products = new ArrayList<>(initialCapacity);
        generateSampleData(initialCapacity);
    }

    private void generateSampleData(int count) {
        String[] sampleNames = {"Ноутбук", "Телефон", "Планшет", "Монитор", "Клавиатура"};
        String[] sampleTags = {"электроника", "офис", "техника", "гаджет", "аксессуар"};
        String[] sampleStatuses = {"в наличии", "под заказ", "нет в наличии"};

        for (int i = 1; i <= count; i++) {
            products.add(new Product(
                    i,
                    sampleNames[random.nextInt(sampleNames.length)],
                    random.nextInt(100),
                    new Tag(i, sampleTags[random.nextInt(sampleTags.length)]),
                    sampleStatuses[random.nextInt(sampleStatuses.length)]
            ));
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addProduct(Product product) {
        int newId = products.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0) + 1;
        product.setId(newId);
        products.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    @Override
    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}