package com.example.praktikalistproducts.dao.impl;

import com.example.praktikalistproducts.dao.ProductDAO;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresProductDAO implements ProductDAO {
    private Connection conn;

    public PostgresProductDAO() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "password");
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/product_db",
                    "postgres",
                    "password");
            initializeDatabase();
        } catch (Exception e) {
            System.err.println("Ошибка при подключении к PostgreSQL:");
            e.printStackTrace();
            conn = null; // Явно указываем, что соединение не установлено
        }
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "count INTEGER," +
                    "tag VARCHAR(255)," +
                    "status VARCHAR(255))");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        if (conn == null) {
            System.err.println("Нет подключения к БД");
            return new ArrayList<>(); // Возвращаем пустой список вместо null
        }

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

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, count, tag, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getCount());
            pstmt.setString(3, product.getTag().getTag());
            pstmt.setString(4, product.getStatus());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении продукта", e);
        }
    }

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
            throw new RuntimeException("Ошибка при обновлении продукта", e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении продукта", e);
        }
    }

    /**
     * Закрывает соединение с базой данных
     */
    public void shutdown() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Соединение с базой данных закрыто");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения");
            e.printStackTrace();
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("count"),
                new Tag(rs.getInt("id"), rs.getString("tag")),
                rs.getString("status")
        );
    }

    private void handleSQLException(SQLException e) {
        System.err.println("SQL ошибка:");
        System.err.println("Сообщение: " + e.getMessage());
        System.err.println("Код ошибки: " + e.getErrorCode());
        System.err.println("Состояние SQL: " + e.getSQLState());
    }
}