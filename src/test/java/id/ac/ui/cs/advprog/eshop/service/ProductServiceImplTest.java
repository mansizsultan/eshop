package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        // Setup
        when(productRepository.create(product)).thenReturn(product);

        // Execute
        Product createdProduct = productService.create(product);

        // Verify
        verify(productRepository, times(1)).create(product);
        assertEquals(product, createdProduct);
    }

    @Test
    void testFindAll() {
        // Setup
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        // Execute
        List<Product> foundProducts = productService.findAll();

        // Verify
        verify(productRepository, times(1)).findAll();
        assertEquals(2, foundProducts.size());
        assertEquals(product.getProductId(), foundProducts.get(0).getProductId());
        assertEquals(product2.getProductId(), foundProducts.get(1).getProductId());
    }

    @Test
    void testFindAllWithEmptyList() {
        // Setup
        List<Product> emptyList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(emptyList.iterator());

        // Execute
        List<Product> foundProducts = productService.findAll();

        // Verify
        verify(productRepository, times(1)).findAll();
        assertTrue(foundProducts.isEmpty());
    }

    @Test
    void testDelete() {
        // Setup
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        doNothing().when(productRepository).delete(productId);

        // Execute
        productService.delete(productId);

        // Verify
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testFindById() {
        // Setup
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Execute
        Optional<Product> foundProduct = productService.findById(productId);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void testFindByIdNotFound() {
        // Setup
        String productId = "non-existent-id";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Execute
        Optional<Product> foundProduct = productService.findById(productId);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testUpdate() {
        // Setup
        when(productRepository.update(product)).thenReturn(product);

        // Execute
        Product updatedProduct = productService.update(product);

        // Verify
        verify(productRepository, times(1)).update(product);
        assertEquals(product, updatedProduct);
    }

    @Test
    void testUpdateNotFound() {
        // Setup
        when(productRepository.update(product)).thenReturn(null);

        // Execute
        Product updatedProduct = productService.update(product);

        // Verify
        verify(productRepository, times(1)).update(product);
        assertNull(updatedProduct);
    }
}