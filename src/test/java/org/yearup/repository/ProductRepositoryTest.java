package org.yearup.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.yearup.models.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Sql(scripts = "classpath:test-insert-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductRepositoryTest {

    @Autowired // <-- Fixed: Removed the comment lines so Spring injects this bean!
    private ProductRepository productRepository;

    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;

        // act
        Product actual = productRepository.findById(productId).orElse(null);

        // assert
        assertNotNull(actual, "Because product 1 should exist in the test database.");
        assertEquals(499.99, actual.getPrice(), 0.001, "Because I tried to get product 1 from the database.");
    }

    @Test
    public void update_shouldPersistNewStockValue_inDatabase()
    {
        // 1. Arrange: Target product ID 1
        int productId = 1;
        Product productToUpdate = productRepository.findById(productId).orElse(null);

        // Safety check: Make sure the test data actually loaded a product
        assertNotNull(productToUpdate, "Product 1 should exist in the test-insert-data.sql script");

        // 2. Act: Modify the stock and save
        productToUpdate.setStock(80);
        productRepository.save(productToUpdate);

        // 3. Assert: Pull fresh from DB to ensure persistence
        Product updatedProduct = productRepository.findById(productId).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals(80, updatedProduct.getStock(), "The test database should successfully update the stock to 80.");
    }
}