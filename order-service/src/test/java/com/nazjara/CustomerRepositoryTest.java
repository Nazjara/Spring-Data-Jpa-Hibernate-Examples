package com.nazjara;

import com.nazjara.model.Customer;
import com.nazjara.model.OrderHeader;
import com.nazjara.model.OrderLine;
import com.nazjara.model.OrderStatus;
import com.nazjara.repository.CustomerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void saveCustomer() {
        var orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);

        var order = new OrderHeader();
        order.setStatus(OrderStatus.NEW);
        order.addOrderLine(orderLine);

        var customer = new Customer();
        customer.setContactInfo("contactInfo");
        customer.addOrder(order);

        var savedCustomer = repository.save(customer);

        assertTrue(savedCustomer.getOrders().size() > 0);
        assertTrue(savedCustomer.getOrders().stream().findFirst().get().getOrderLines().size() > 0);
    }
}
