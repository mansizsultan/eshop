package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
}