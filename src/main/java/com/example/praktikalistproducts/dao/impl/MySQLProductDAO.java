package com.example.praktikalistproducts.dao.impl;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductDAO implements ProductDAO {
    private Connection conn;

    // Конструктор - устанавливает соединение с MySQL
    public MySQLProductDAO() {
        String url = "jdbc:mysql://localhost:3306/product_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "your_password";

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Подключение к MySQL через DBeaver успешно!");
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к MySQL:");
            e.printStackTrace();
        }
    }

    // Создает таблицу products, если она не существует
    private void initializeDatabase() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "count INT," +
                    "tag VARCHAR(255)," +
                    "status VARCHAR(255))");
        }
    }

    // Получает все продукты из таблицы
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("count"),
                        new Tag(rs.getInt("id"), rs.getString("tag")),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении продуктов:");
            e.printStackTrace();
        }
        return products;
    }

    // Добавляет новый продукт в таблицу
    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, count, tag, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getCount());
            pstmt.setString(3, product.getTag().getTag());
            pstmt.setString(4, product.getStatus());
            pstmt.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении продукта:");
            e.printStackTrace();
        }
    }

    // Обновляет существующий продукт
    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, count = ?, tag = ?, status = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getCount());
            pstmt.setString(3, product.getTag().getTag());
            pstmt.setString(4, product.getStatus());
            pstmt.setInt(5, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении продукта:");
            e.printStackTrace();
        }
    }

    // Удаляет продукт по ID
    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении продукта:");
            e.printStackTrace();
        }
    }

    // Получает продукт по ID
    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("count"),
                            new Tag(rs.getInt("id"), rs.getString("tag")),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении продукта по ID:");
            e.printStackTrace();
        }
        return null;
    }

    // Закрывает соединение с базой данных
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Соединение с MySQL закрыто");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения:");
            e.printStackTrace();
        }
    }
}