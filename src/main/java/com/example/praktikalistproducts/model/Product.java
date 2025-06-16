package com.example.praktikalistproducts.model;

import javafx.beans.property.*;

/**
 * Класс, представляющий продукт с его характеристиками.
 * Использует JavaFX свойства для поддержки двустороннего связывания данных.
 */
public class Product {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty count = new SimpleIntegerProperty();
    private final ObjectProperty<Tag> tag = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    /**
     * Конструктор продукта.
     *
     * @param id уникальный идентификатор продукта
     * @param name название продукта
     * @param count количество продукта
     * @param tag тег продукта
     * @param status статус продукта
     */
    public Product(int id, String name, int count, Tag tag, String status) {
        this.id.set(id);
        this.name.set(name);
        this.count.set(count);
        this.tag.set(tag);
        this.status.set(status);
    }

    // Property getters

    /**
     * Получает свойство идентификатора для JavaFX привязок.
     * @return IntegerProperty идентификатора продукта
     */
    public IntegerProperty idProperty() { return id; }

    /**
     * Получает свойство названия для JavaFX привязок.
     * @return StringProperty названия продукта
     */
    public StringProperty nameProperty() { return name; }

    /**
     * Получает свойство количества для JavaFX привязок.
     * @return IntegerProperty количества продукта
     */
    public IntegerProperty countProperty() { return count; }

    /**
     * Получает свойство тега для JavaFX привязок.
     * @return ObjectProperty<Tag> тега продукта
     */
    public ObjectProperty<Tag> tagProperty() { return tag; }

    /**
     * Получает свойство статуса для JavaFX привязок.
     * @return StringProperty статуса продукта
     */
    public StringProperty statusProperty() { return status; }

    // Regular getters and setters

    /**
     * Получает идентификатор продукта.
     * @return числовой идентификатор продукта
     */
    public int getId() { return id.get(); }

    /**
     * Устанавливает идентификатор продукта.
     * @param id новый идентификатор продукта
     */
    public void setId(int id) { this.id.set(id); }

    /**
     * Получает название продукта.
     * @return строковое название продукта
     */
    public String getName() { return name.get(); }

    /**
     * Устанавливает название продукта.
     * @param name новое название продукта
     */
    public void setName(String name) { this.name.set(name); }

    /**
     * Получает количество продукта.
     * @return числовое количество продукта
     */
    public int getCount() { return count.get(); }

    /**
     * Устанавливает количество продукта.
     * @param count новое количество продукта
     */
    public void setCount(int count) { this.count.set(count); }

    /**
     * Получает тег продукта.
     * @return объект Tag продукта
     */
    public Tag getTag() { return tag.get(); }

    /**
     * Устанавливает тег продукта.
     * @param tag новый тег продукта
     */
    public void setTag(Tag tag) { this.tag.set(tag); }

    /**
     * Получает статус продукта.
     * @return строковый статус продукта
     */
    public String getStatus() { return status.get(); }

    /**
     * Устанавливает статус продукта.
     * @param status новый статус продукта
     */
    public void setStatus(String status) { this.status.set(status); }
}