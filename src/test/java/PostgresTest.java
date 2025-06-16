import com.example.praktikalistproducts.dao.impl.PostgresProductDAO;
import com.example.praktikalistproducts.model.Product;
import com.example.praktikalistproducts.model.Tag;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostgresTest {
    private PostgresProductDAO dao;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        dao = new PostgresProductDAO();
        cleanTestData();

        Tag testTag = new Tag(999, "Test Tag");
        testProduct = new Product(999, "Test Product", 10, testTag, "Test Status");
        dao.addProduct(testProduct);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = dao.getAllProducts();
        assertFalse(products.isEmpty());
    }

    @Test
    void testGetProductById() {
        Product retrieved = dao.getProductById(testProduct.getId());
        assertNotNull(retrieved);
        assertEquals("Test Product", retrieved.getName());
    }

    @Test
    void testUpdateProduct() {
        testProduct.setName("Updated Product");
        dao.updateProduct(testProduct);

        Product updated = dao.getProductById(testProduct.getId());
        assertEquals("Updated Product", updated.getName());
    }

    @Test
    void testDeleteProduct() {
        Product tempProduct = new Product(1000, "Temp Product", 5,
                new Tag(1000, "Temp Tag"), "Temp Status");
        dao.addProduct(tempProduct);
        dao.deleteProduct(tempProduct.getId());
        assertNull(dao.getProductById(tempProduct.getId()));
    }

    @AfterEach
    void cleanTestData() {
        try {
            if (testProduct != null) {
                dao.deleteProduct(testProduct.getId());
            }
            dao.deleteProduct(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}