package com.nazjara;

import com.nazjara.model.OrderHeader;
import com.nazjara.repository.OrderHeaderRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository repository;

    @Test
    void orderHeaderRepositoryCount() {
        var initialCount = repository.count();
        repository.save(new OrderHeader());
        assertThat(repository.count(), greaterThan(initialCount));
    }
}
