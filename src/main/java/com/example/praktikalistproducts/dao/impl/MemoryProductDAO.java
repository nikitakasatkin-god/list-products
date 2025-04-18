package com.example.praktikalistproducts.dao.impl;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Реализация ProductDAO для хранения продуктов в оперативной памяти.
 * Используется для тестирования или как временное хранилище данных.
 */
public class MemoryProductDAO implements ProductDAO {
    private final List<Product> products = new ArrayList<>();

    /**
     * Конструктор, инициализирующий тестовые данные в памяти.
     * Создает три тестовых продукта при инициализации.
     */
    public MemoryProductDAO() {
        // Тестовые данные
        products.add(new Product(1, "Ноутбук", 5, new Tag(1, "электроника"), "в наличии"));
        products.add(new Product(2, "Мышь", 10, new Tag(2, "аксессуар"), "в наличии"));
        products.add(new Product(3, "Клавиатура", 7, new Tag(3, "аксессуар"), "под заказ"));
    }

    /*private void generateSampleData(int count) {
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
    }*/

    /**
     * Получает список всех продуктов из памяти.
     * @return новый список всех продуктов (копия оригинального списка)
     */
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Находит продукт по его идентификатору.
     * @param id идентификатор продукта для поиска
     * @return найденный продукт или null, если продукт не найден
     */
    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Добавляет новый продукт в память.
     * Автоматически генерирует новый идентификатор для продукта.
     * @param product продукт для добавления (идентификатор игнорируется)
     * @throws IllegalArgumentException если продукт равен null
     */
    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Продукт не может быть null");
        }

        int newId = products.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0) + 1;
        product.setId(newId);
        products.add(product);
    }

    /**
     * Обновляет существующий продукт в памяти.
     * @param product продукт с обновленными данными
     * @throws IllegalArgumentException если продукт равен null или не найден
     */
    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Продукт не может быть null");
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
        throw new IllegalArgumentException("Продукт с id=" + product.getId() + " не найден");
    }

    /**
     * Удаляет продукт из памяти по идентификатору.
     * @param id идентификатор продукта для удаления
     */
    @Override
    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}