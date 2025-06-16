package com.example.praktikalistproducts.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс, представляющий тег продукта.
 * Содержит идентификатор тега и его текстовое значение.
 * Поддерживает JavaFX свойства для интеграции с графическим интерфейсом.
 */
public class Tag {
    private final int id;
    private final StringProperty tag = new SimpleStringProperty();

    /**
     * Конструктор для создания нового тега.
     * @param id уникальный идентификатор тега
     * @param tag текстовое значение тега
     */
    public Tag(int id, String tag) {
        this.id = id;
        this.tag.set(tag);
    }

    /**
     * Получение идентификатора тега.
     * @return числовой идентификатор тега
     */
    public int getId() { return id; }

    /**
     * Получение текстового значения тега.
     * @return строковое значение тега
     */
    public String getTag() { return tag.get(); }

    /**
     * Установка текстового значения тега.
     * @param tag новое строковое значение тега
     */
    public void setTag(String tag) { this.tag.set(tag); }

    /**
     * Получение свойства тега для JavaFX привязок.
     * @return StringProperty содержащий значение тега
     */
    public StringProperty tagProperty() { return tag; }
}