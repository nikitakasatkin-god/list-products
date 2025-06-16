module com.example.praktikalistproducts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.praktikalistproducts to javafx.fxml;
    exports com.example.praktikalistproducts;
    exports com.example.praktikalistproducts.model;
    exports com.example.praktikalistproducts.controller;
    exports com.example.praktikalistproducts.dao.impl;
    opens com.example.praktikalistproducts.controller to javafx.fxml;
}