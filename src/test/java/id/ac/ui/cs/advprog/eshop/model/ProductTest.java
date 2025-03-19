package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("573e7b70-589f-49c2-8385-319533c676be");
        this.product.setProductName("Playstation 5");
        this.product.setProductQuantity(10);
    }

    @Test
    void testGetProductId() {
        assertEquals("573e7b70-589f-49c2-8385-319533c676be", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Playstation 5", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(10, this.product.getProductQuantity());
    }

    @Test
    void testSetProductName() {
        this.product.setProductName("Xbox Series X");
        assertEquals("Xbox Series X", this.product.getProductName());
    }

    @Test
    void testSetProductQuantity() {
        this.product.setProductQuantity(5);
        assertEquals(5, this.product.getProductQuantity());
    }

    @Test
    void testConstructorWithParameters() {
        Product newProduct = new Product("12345", "Nintendo Switch", 3);
        assertEquals("12345", newProduct.getProductId());
        assertEquals("Nintendo Switch", newProduct.getProductName());
        assertEquals(3, newProduct.getProductQuantity());
    }

    @Test
    void testConstructorWithNullId() {
        Product newProduct = new Product(null, "Steam Deck", 2);
        assertNotNull(newProduct.getProductId());
        assertDoesNotThrow(() -> UUID.fromString(newProduct.getProductId()));
        assertEquals("Steam Deck", newProduct.getProductName());
        assertEquals(2, newProduct.getProductQuantity());
    }
}
