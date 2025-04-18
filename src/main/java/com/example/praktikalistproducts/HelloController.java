package com.example.praktikalistproducts;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.dao.ProductFactory;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Tooltip;

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
    @FXML private Button clearFilterButton;

    @FXML private RadioButton dbRadio;
    @FXML private RadioButton fileRadio;
    @FXML private RadioButton ramRadio;

    private ProductDAO productDAO;
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<String> tags = FXCollections.observableArrayList();
    private FilteredList<Product> filteredProducts;

    /**
     * Перечисление подсказок для кнопок интерфейса
     */
    private enum ButtonHint {
        ADD_BUTTON("Добавить новый продукт"),
        EDIT_BUTTON("Редактировать выбранный продукт"),
        DELETE_BUTTON("Удалить выбранный продукт"),
        FILTER_BUTTON("Фильтровать продукты по выбранному тегу"),
        CLEAR_FILTER_BUTTON("Сбросить фильтр"),
        DB_RADIO("Использовать базу данных для хранения"),
        FILE_RADIO("Использовать файл для хранения"),
        RAM_RADIO("Использовать оперативную память для хранения");

        private final String hint;

        ButtonHint(String hint) {
            this.hint = hint;
        }

        public String getHint() {
            return hint;
        }
    }

    /**
     * Инициализация контроллера, настройка таблицы и элементов управления
     */
    @FXML
    public void initialize() {
        // Настройка колонок таблицы
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTag().tagProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Инициализация FilteredList
        filteredProducts = new FilteredList<>(products);
        table.setItems(filteredProducts);

        // Настройка ToggleGroup для RadioButton
        ToggleGroup dataSourceGroup = new ToggleGroup();
        dbRadio.setToggleGroup(dataSourceGroup);
        fileRadio.setToggleGroup(dataSourceGroup);
        ramRadio.setToggleGroup(dataSourceGroup);
        dbRadio.setSelected(true);

        // Настройка Tooltip для кнопок
        setupTooltips();

        // Инициализация данных
        changeDataSource();

        // Настройка обработчиков событий
        setupEventHandlers();
    }

    /**
     * Настройка всплывающих подсказок для элементов управления
     */
    private void setupTooltips() {
        setTooltipForControl(addButton, ButtonHint.ADD_BUTTON);
        setTooltipForControl(editButton, ButtonHint.EDIT_BUTTON);
        setTooltipForControl(deleteButton, ButtonHint.DELETE_BUTTON);
        setTooltipForControl(filterButton, ButtonHint.FILTER_BUTTON);
        setTooltipForControl(clearFilterButton, ButtonHint.CLEAR_FILTER_BUTTON);
        setTooltipForControl(dbRadio, ButtonHint.DB_RADIO);
        setTooltipForControl(fileRadio, ButtonHint.FILE_RADIO);
        setTooltipForControl(ramRadio, ButtonHint.RAM_RADIO);
    }

    /**
     * Установка всплывающей подсказки для конкретного элемента управления
     * @param control элемент управления
     * @param buttonHint тип подсказки из перечисления ButtonHint
     */
    private void setTooltipForControl(Control control, ButtonHint buttonHint) {
        String hintText;
        switch (buttonHint) {
            case ADD_BUTTON:
                hintText = ButtonHint.ADD_BUTTON.getHint();
                break;
            case EDIT_BUTTON:
                hintText = ButtonHint.EDIT_BUTTON.getHint();
                break;
            case DELETE_BUTTON:
                hintText = ButtonHint.DELETE_BUTTON.getHint();
                break;
            case FILTER_BUTTON:
                hintText = ButtonHint.FILTER_BUTTON.getHint();
                break;
            case CLEAR_FILTER_BUTTON:
                hintText = ButtonHint.CLEAR_FILTER_BUTTON.getHint();
                break;
            case DB_RADIO:
                hintText = ButtonHint.DB_RADIO.getHint();
                break;
            case FILE_RADIO:
                hintText = ButtonHint.FILE_RADIO.getHint();
                break;
            case RAM_RADIO:
                hintText = ButtonHint.RAM_RADIO.getHint();
                break;
            default:
                throw new IllegalArgumentException("Unknown button hint: " + buttonHint);
        }
        control.setTooltip(new Tooltip(hintText));
    }

    /**
     * Настройка обработчиков событий для кнопок и таблицы
     */
    private void setupEventHandlers() {
        addButton.setOnAction(event -> addProduct());
        editButton.setOnAction(event -> editProduct());
        deleteButton.setOnAction(event -> deleteProduct());
        filterButton.setOnAction(event -> filterProducts());
        clearFilterButton.setOnAction(event -> clearFilter());

        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillFields(newSelection);
                    }
                });
    }

    /**
     * Изменение источника данных в зависимости от выбранного RadioButton
     */
    @FXML
    private void changeDataSource() {
        String selectedSource;
        if (dbRadio.isSelected()) {
            selectedSource = ProductFactory.DB;
        } else if (fileRadio.isSelected()) {
            selectedSource = ProductFactory.MYSQL;
        } else {
            selectedSource = ProductFactory.RAM;
        }

        try {
            productDAO = ProductFactory.createProductDAO(selectedSource);
            refreshData();
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось подключиться к источнику данных",
                    "Используется хранение в памяти");
            ramRadio.setSelected(true);
            productDAO = ProductFactory.createProductDAO(ProductFactory.RAM);
            refreshData();
        }
    }

    /**
     * Обновление данных в таблице из текущего источника данных
     */
    private void refreshData() {
        products.setAll(productDAO.getAllProducts());
        updateTagsList();
    }

    /**
     * Обновление списка тегов для ComboBox фильтрации
     */
    private void updateTagsList() {
        tags.clear();
        products.stream()
                .map(p -> p.getTag().getTag())
                .distinct()
                .forEach(tags::add);
        tagFilterComboBox.setItems(tags);
    }

    /**
     * Добавление нового продукта на основе данных из полей ввода
     */
    private void addProduct() {
        try {
            String name = nameField.getText().trim();
            int count = Integer.parseInt(countField.getText().trim());
            String tag = tagField.getText().trim();
            String status = statusField.getText().trim();

            if (name.isEmpty() || tag.isEmpty() || status.isEmpty()) {
                showAlert("Ошибка", "Пустые поля", "Все поля должны быть заполнены");
                return;
            }

            Product newProduct = new Product(0, name, count, new Tag(0, tag), status);
            productDAO.addProduct(newProduct);
            refreshData();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректное количество", "Введите число в поле 'Количество'");
        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка добавления", e.getMessage());
        }
    }

    /**
     * Редактирование выбранного продукта
     */
    private void editProduct() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Не выбран продукт", "Выберите продукт для редактирования");
            return;
        }

        try {
            String name = nameField.getText().trim();
            int count = Integer.parseInt(countField.getText().trim());
            String tag = tagField.getText().trim();
            String status = statusField.getText().trim();

            if (name.isEmpty() || tag.isEmpty() || status.isEmpty()) {
                showAlert("Ошибка", "Пустые поля", "Все поля должны быть заполнены");
                return;
            }

            selected.setName(name);
            selected.setCount(count);
            selected.getTag().setTag(tag);
            selected.setStatus(status);

            productDAO.updateProduct(selected);
            refreshData();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректное количество", "Введите число в поле 'Количество'");
        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка редактирования", e.getMessage());
        }
    }

    /**
     * Удаление выбранного продукта
     */
    private void deleteProduct() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Не выбран продукт", "Выберите продукт для удаления");
            return;
        }

        try {
            productDAO.deleteProduct(selected.getId());
            refreshData();
            clearFields();
        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка удаления", e.getMessage());
        }
    }

    /**
     * Фильтрация продуктов по выбранному тегу
     */
    private void filterProducts() {
        String selectedTag = tagFilterComboBox.getSelectionModel().getSelectedItem();
        if (selectedTag != null && !selectedTag.isEmpty()) {
            filteredProducts.setPredicate(product ->
                    product.getTag().getTag().equalsIgnoreCase(selectedTag));
        }
    }

    /**
     * Сброс фильтрации продуктов
     */
    private void clearFilter() {
        filteredProducts.setPredicate(null);
        tagFilterComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Заполнение полей ввода данными выбранного продукта
     * @param product выбранный продукт из таблицы
     */
    private void fillFields(Product product) {
        nameField.setText(product.getName());
        countField.setText(String.valueOf(product.getCount()));
        tagField.setText(product.getTag().getTag());
        statusField.setText(product.getStatus());
    }

    /**
     * Очистка полей ввода
     */
    private void clearFields() {
        nameField.clear();
        countField.clear();
        tagField.clear();
        statusField.clear();
    }

    /**
     * Отображение диалогового окна с сообщением об ошибке
     * @param title заголовок окна
     * @param header заголовок сообщения
     * @param content текст сообщения
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}