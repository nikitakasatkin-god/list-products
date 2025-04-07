package com.example.praktikalistproducts;

import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML private TableView<Product> table;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> countColumn;
    @FXML private TableColumn<Product, String> tagColumn;
    @FXML private TableColumn<Product, String> statusColumn;

    @FXML private TextField nameField;
    @FXML private TextField countField;
    @FXML private TextField tagField;
    @FXML private TextField statusField;

    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private ComboBox<String> tagFilterComboBox;
    @FXML private Button filterButton;

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<String> tags = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Настройка колонок таблицы
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTag().tagProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Загрузка тестовых данных
        loadSampleData();

        // Настройка ComboBox
        updateTagsList();
        tagFilterComboBox.setItems(tags);

        // Настройка обработчиков событий
        setupEventHandlers();
    }

    private void loadSampleData() {
        products.addAll(
                new Product(1, "Ноутбук", 5, new Tag(1, "электроника"), "в наличии"),
                new Product(2, "Мышь", 10, new Tag(2, "аксессуар"), "в наличии"),
                new Product(3, "Клавиатура", 7, new Tag(3, "аксессуар"), "под заказ")
        );

        table.setItems(products);
    }

    private void updateTagsList() {
        tags.clear();
        products.stream()
                .map(p -> p.getTag().getTag())
                .distinct()
                .forEach(tags::add);
    }

    private void setupEventHandlers() {
        addButton.setOnAction(e -> addProduct());
        editButton.setOnAction(e -> editProduct());
        deleteButton.setOnAction(e -> deleteProduct());
        filterButton.setOnAction(e -> filterProducts());

        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillFields(newSelection);
                    }
                });
    }

    private void addProduct() {
        try {
            Product product = new Product(
                    products.size() + 1,
                    nameField.getText(),
                    Integer.parseInt(countField.getText()),
                    new Tag(products.size() + 1, tagField.getText()),
                    statusField.getText()
            );

            products.add(product);
            updateTagsList();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректное количество", "Введите число в поле 'Количество'");
        }
    }

    private void editProduct() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setName(nameField.getText());
                selected.setCount(Integer.parseInt(countField.getText()));
                selected.getTag().setTag(tagField.getText());
                selected.setStatus(statusField.getText());

                table.refresh();
                updateTagsList();
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Некорректное количество", "Введите число в поле 'Количество'");
            }
        }
    }

    private void deleteProduct() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            products.remove(selected);
            updateTagsList();
            clearFields();
        }
    }

    private void filterProducts() {
        String selectedTag = tagFilterComboBox.getSelectionModel().getSelectedItem();
        if (selectedTag != null && !selectedTag.isEmpty()) {
            table.setItems(products.filtered(p -> p.getTag().getTag().equals(selectedTag)));
        } else {
            table.setItems(products);
        }
    }

    private void fillFields(Product product) {
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

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}