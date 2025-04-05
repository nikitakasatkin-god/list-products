module com.example.praktikalistproducts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.praktikalistproducts to javafx.fxml;
    exports com.example.praktikalistproducts;
}