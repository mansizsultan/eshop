package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("573e7b70-589f-49c2-8385-319533c676be");
        product.setProductName("Playstation 5");
        product.setProductQuantity(10);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("573e7b70-589f-49c2-8385-319533c676be");
        product1.setProductName("Playstation 5");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("87c1e40b-b403-4af5-b12d-8de6dfb699bc");
        product2.setProductName("iPhone 16");
        product2.setProductQuantity(5);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductName("iPhone 16");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("iPhone 17");
        updatedProduct.setProductQuantity(5);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("iPhone 17", result.getProductName());
        assertEquals(5, result.getProductQuantity());
    }

    @Test
    void testEditProductFailure() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("e3274332-7c8c-4d38-b3e7-1b2f3e9fd431"); // Non Existent ID
        updatedProduct.setProductName("Samsung A55");
        updatedProduct.setProductQuantity(100);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductName("DELETED FILES");
        product.setProductQuantity(42);
        product = productRepository.create(product);

        productRepository.delete(product.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductFailure() {
        Product product = new Product();
        product.setProductName("Samsung S23 Ultra");
        product.setProductQuantity(1);
        product = productRepository.create(product);

        productRepository.delete("e3274332-7c8c-4d38-b3e7-1b2f3e9fd431"); // Non Existent ID

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }
}