package com.example.microservices.demo.service;

import com.example.microservices.demo.dto.ProductRequest;
import com.example.microservices.demo.dto.ProductResponse;
import com.example.microservices.demo.model.Product;
import com.example.microservices.demo.repo.ProductRepository;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreatProduct() {
        ProductRequest productRequest = new ProductRequest("1", "Product1", "Description1", new BigDecimal("100.0"));

        // The Product to be returned by the mock when save() is called
        Product savedProduct = Product.builder()
                .id(null) // Setting the expected id
                .name("Product1")
                .description("Description1")
                .price(new BigDecimal("100.0"))
                .build();

        // Stub the save method
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        ProductResponse productResponse = productService.creatProduct(productRequest);

        // Then
        assertAll(
                () -> assertEquals(null, productResponse.id(), "Product ID should be 12345"),
                () -> assertEquals("Product1", productResponse.name(), "Product name should match"),
                () -> assertEquals("Description1", productResponse.description(), "Product description should match"),
                () -> assertEquals(new BigDecimal("100.0"), productResponse.price(), "Product price should match")
        );

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void ShouldThrowNullProductRequestIfNoProduct() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            productService.creatProduct(null);
        });
    }


}