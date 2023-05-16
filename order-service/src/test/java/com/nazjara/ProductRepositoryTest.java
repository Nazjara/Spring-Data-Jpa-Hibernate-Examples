package com.nazjara;

import com.nazjara.model.Product;
import com.nazjara.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void productRepositoryCount() {
        var initialCount = repository.count();
        repository.save(new Product());
        assertThat(repository.count(), greaterThan(initialCount));
    }

    @Test
    void getByDescription() {
        var product = repository.findByDescription("PRODUCT1");

        assertNotNull(product);
        assertTrue(product.getCategories().size() > 0);
    }
}
