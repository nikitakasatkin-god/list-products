package com.example.praktikalistproducts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {
    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Integer> countColumn;

    @FXML
    private TableColumn<Product, String> tagColumn;

    @FXML
    private TableColumn<Product, String> statusColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField countField;

    @FXML
    private TextField tagField;

    @FXML
    private TextField statusField;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton; // Новая кнопка "Удалить"

    @FXML
    private ComboBox<String> tagFilterComboBox;

    @FXML
    private Button filterButton;

    private ListProduct listProduct = new ListProduct();

    private int nextId = 1;

    private ObservableList<String> tags = FXCollections.observableArrayList(); // Список тегов

    @FXML
    public void initialize() {
        // Настройка колонок таблицы
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTag().tagProperty());
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Загрузка данных в таблицу
        table.setItems(listProduct.getProducts());

        // Обработка кнопки "Добавить"
        addButton.setOnAction(event -> addProduct());

        // Обработка кнопки "Изменить"
        editButton.setOnAction(event -> editProduct());

        // Обработка кнопки "Удалить"
        deleteButton.setOnAction(event -> deleteProduct());

        // Заполнение текстовых полей при выборе строки
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFieldsWithSelectedProduct(newSelection);
            }
        });

        // Инициализация ComboBox с тегами
        tagFilterComboBox.setItems(tags);

        // Обработка кнопки "Фильтровать по тегу"
        filterButton.setOnAction(event -> filterProductsByTag());
    }

    private void addProduct() {
        String name = nameField.getText();
        int count = Integer.parseInt(countField.getText());
        String tagText = tagField.getText();
        String status = statusField.getText();

        // Создаем объект Tag
        Tag tag = new Tag(nextId, tagText);

        // Создаем продукт с уникальным id
        Product product = new Product(nextId, name, count, tag, status);
        listProduct.addProduct(product);

        // Добавляем тег в ComboBox, если его еще нет
        if (!tags.contains(tagText)) {
            tags.add(tagText);
        }

        // Увеличиваем id для следующего товара
        nextId++;

        // Очистка полей ввода
        clearFields();
    }

    private void editProduct() {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Обновляем данные выбранного продукта
            selectedProduct.setName(nameField.getText());
            selectedProduct.setCount(Integer.parseInt(countField.getText()));
            selectedProduct.getTag().setTag(tagField.getText());
            selectedProduct.setStatus(statusField.getText());

            // Обновляем таблицу
            table.refresh();

            // Очистка полей ввода
            clearFields();
        }
    }

    private void deleteProduct() {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Удаляем продукт из списка
            listProduct.getProducts().remove(selectedProduct);

            // Обновляем таблицу
            table.refresh();

            // Очистка полей ввода
            clearFields();
        }
    }

    private void fillFieldsWithSelectedProduct(Product product) {
        nameField.setText(product.getName());
        countField.setText(String.valueOf(product.getCount()));
        tagField.setText(product.getTag().getTag());
        statusField.setText(product.getStatus());
    }

    private void clearFields() {
        nameField.clear();
        countField.clear();
        tagField.clear();
        statusField.clear();
    }

    private void filterProductsByTag() {
        String selectedTag = tagFilterComboBox.getSelectionModel().getSelectedItem();
        if (selectedTag != null && !selectedTag.isEmpty()) {
            // Фильтруем товары по выбранному тегу
            FilteredList<Product> filteredProducts = listProduct.getProducts().filtered(
                    product -> product.getTag().getTag().equals(selectedTag)
            );
            table.setItems(filteredProducts);
        } else {
            // Если тег не выбран, показываем все товары
            table.setItems(listProduct.getProducts());
        }
    }
}