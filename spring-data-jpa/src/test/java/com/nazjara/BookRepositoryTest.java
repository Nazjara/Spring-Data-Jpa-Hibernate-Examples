package com.nazjara;

import com.nazjara.model.Book;
import com.nazjara.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    @Commit
    void bookRepositoryCount1() {
        var initialCount = bookRepository.count();
        bookRepository.save(new Book());
        assertThat(bookRepository.count()).isGreaterThan(initialCount);
    }

    @Test
    @Order(2)
    void bookRepositoryCount2() {
        assertThat(bookRepository.count()).isEqualTo(6);
    }
}
