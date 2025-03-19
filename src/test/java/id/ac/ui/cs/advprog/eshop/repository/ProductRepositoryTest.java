package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Clear any existing products for each test
        Iterator<Product> iterator = productRepository.findAll();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            productRepository.delete(product.getProductId());
        }
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
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
    void testFindById() {
        Product product = new Product();
        product.setProductId("test-id-123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Optional<Product> foundProduct = productRepository.findById("test-id-123");
        assertTrue(foundProduct.isPresent());
        assertEquals("test-id-123", foundProduct.get().getProductId());
        assertEquals("Test Product", foundProduct.get().getProductName());
        assertEquals(10, foundProduct.get().getProductQuantity());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Product> foundProduct = productRepository.findById("non-existent-id");
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testFindByIdWithNullId() {
        // Jika implementasi menangani null ID dengan mengembalikan Optional.empty()
        Optional<Product> foundProduct = productRepository.findById(null);
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testCreateWithNullProduct() {
        // Jika implementasi menangani null product dengan mengembalikan null
        // atau melakukan hal lain selain melempar exception
        Product product = null;
        Product result = productRepository.create(product);
        assertNull(result);
    }

    @Test
    void testUpdateSuccess() {
        Product product = new Product();
        product.setProductId("update-id");
        product.setProductName("Original Name");
        product.setProductQuantity(5);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("update-id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(10);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("update-id", result.getProductId());
        assertEquals("Updated Name", result.getProductName());
        assertEquals(10, result.getProductQuantity());

        // Verify the update persisted
        Optional<Product> retrieved = productRepository.findById("update-id");
        assertTrue(retrieved.isPresent());
        assertEquals("Updated Name", retrieved.get().getProductName());
        assertEquals(10, retrieved.get().getProductQuantity());
    }

    @Test
    void testUpdateFailureProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(10);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdateWithNullProduct() {
        // Uji kasus update dengan product null
        Product result = productRepository.update(null);
        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("delete-id");
        product.setProductName("Product to Delete");
        product.setProductQuantity(3);
        productRepository.create(product);

        // Verify product exists
        Optional<Product> beforeDelete = productRepository.findById("delete-id");
        assertTrue(beforeDelete.isPresent());

        // Delete the product
        productRepository.delete("delete-id");

        // Verify product no longer exists
        Optional<Product> afterDelete = productRepository.findById("delete-id");
        assertFalse(afterDelete.isPresent());
    }

    @Test
    void testDeleteWithNonExistentId() {
        // Should not throw exception
        assertDoesNotThrow(() -> productRepository.delete("non-existent-id"));
    }

    @Test
    void testDeleteWithNullId() {
        // Uji delete dengan null ID, seharusnya tidak throw exception
        assertDoesNotThrow(() -> productRepository.delete(null));
    }

    @Test
    void testUpdateWithMultipleProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(3);
        productRepository.create(product3);

        // Update the middle product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-2");
        updatedProduct.setProductName("Updated Product 2");
        updatedProduct.setProductQuantity(22);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Product 2", result.getProductName());
        assertEquals(22, result.getProductQuantity());

        // Verify other products weren't changed
        Optional<Product> p1 = productRepository.findById("id-1");
        assertTrue(p1.isPresent());
        assertEquals("Product 1", p1.get().getProductName());

        Optional<Product> p3 = productRepository.findById("id-3");
        assertTrue(p3.isPresent());
        assertEquals("Product 3", p3.get().getProductName());
    }

    @Test
    void testDeleteWithMultipleProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("multi-id-1");
        product1.setProductName("Multi Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("multi-id-2");
        product2.setProductName("Multi Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("multi-id-3");
        product3.setProductName("Multi Product 3");
        product3.setProductQuantity(30);
        productRepository.create(product3);

        // Delete the middle product
        productRepository.delete("multi-id-2");

        // Verify it was deleted
        Optional<Product> deleted = productRepository.findById("multi-id-2");
        assertFalse(deleted.isPresent());

        // Verify others still exist
        Optional<Product> p1 = productRepository.findById("multi-id-1");
        assertTrue(p1.isPresent());

        Optional<Product> p3 = productRepository.findById("multi-id-3");
        assertTrue(p3.isPresent());

        // Check the iterator returns the correct products
        Iterator<Product> iterator = productRepository.findAll();
        int count = 0;
        while (iterator.hasNext()) {
            Product p = iterator.next();
            assertNotEquals("multi-id-2", p.getProductId());
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testUpdateFirstProduct() {
        // Test updating the first product in the list
        Product product1 = new Product();
        product1.setProductId("first-id");
        product1.setProductName("First Product");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("second-id");
        product2.setProductName("Second Product");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("first-id");
        updatedProduct.setProductName("Updated First");
        updatedProduct.setProductQuantity(11);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated First", result.getProductName());

        // Check it was updated in the repository
        Optional<Product> retrieved = productRepository.findById("first-id");
        assertTrue(retrieved.isPresent());
        assertEquals("Updated First", retrieved.get().getProductName());
    }

    @Test
    void testUpdateLastProduct() {
        // Test updating the last product in the list
        Product product1 = new Product();
        product1.setProductId("first-id");
        product1.setProductName("First Product");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("last-id");
        product2.setProductName("Last Product");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("last-id");
        updatedProduct.setProductName("Updated Last");
        updatedProduct.setProductQuantity(22);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Last", result.getProductName());

        // Check it was updated in the repository
        Optional<Product> retrieved = productRepository.findById("last-id");
        assertTrue(retrieved.isPresent());
        assertEquals("Updated Last", retrieved.get().getProductName());
    }
}