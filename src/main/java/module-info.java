module com.example.praktikalistproducts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.praktikalistproducts to javafx.fxml;
    exports com.example.praktikalistproducts;
    exports com.example.praktikalistproducts.model;
}