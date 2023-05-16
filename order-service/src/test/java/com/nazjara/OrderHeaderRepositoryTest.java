package com.nazjara;

import com.nazjara.model.*;
import com.nazjara.repository.OrderHeaderRepository;
import com.nazjara.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        var testProduct = new Product();
        testProduct.setStatus(ProductStatus.NEW);
        testProduct.setDescription("test product");
        product = productRepository.saveAndFlush(testProduct);
    }

    @Test
    void orderHeaderRepositoryCount() {
        var initialCount = orderHeaderRepository.count();
        orderHeaderRepository.saveAndFlush(new OrderHeader());
        assertThat(orderHeaderRepository.count(), greaterThan(initialCount));
    }

    @Test
    void saveOrderHeaderWithLineAndApproval() {
        var orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");

        var orderHeader = new OrderHeader();
        var orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderHeader.addOrderLine(orderLine);
        orderLine.setProduct(product);
        orderHeader.setOrderApproval(orderApproval);

        var savedOrderHeader = orderHeaderRepository.saveAndFlush(orderHeader);

        assertEquals(savedOrderHeader.getOrderLines().size(), 1);
        assertNotNull(savedOrderHeader.getOrderLines().stream().findFirst().get().getProduct());
        assertNotNull(savedOrderHeader.getOrderApproval());
    }
}
