package com.nazjara;

import com.nazjara.dao.BookDao;
import com.nazjara.model.Author;
import com.nazjara.model.Book;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.nazjara.dao")
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void getById() {
        var book = bookDao.getById(1L);
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitle() {
        var book = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitleFuture() throws ExecutionException, InterruptedException {
        var book = bookDao.getByTitleAsync("Spring in Action, 5th Edition").get();
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitlePositionalParameterQuery() {
        var book = bookDao.getByTitlePositionalParameterQuery("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitleNamedParameterQuery() {
        var book = bookDao.getByTitleNamedParameterQuery("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitleNativeQuery() {
        var book = bookDao.getByTitleNativeQuery("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getAllNamedQuery() {
        var book = bookDao.namedGetAll();
        assertThat(book.size(), greaterThan(0));
    }

    @Test
    void getByTitleNamedQuery() {
        var book = bookDao.namedGetByTitle("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitleNotFound() {
        assertThrows(EntityNotFoundException.class, () -> bookDao.getByTitle("Unknown"));
    }

    @Test
    void getByTitleNotNull() {
        assertThat(bookDao.getByTitleNotNull().count(), greaterThan(0L));
    }

    @Test
    void save() {
        var author = new Author();
        author.setId(3L);

        var book = bookDao.save(new Book("Title", "Isbn", "Publisher", author));
        assertNotNull(book.getId());
        assertEquals(book.getTitle(), "Title");
        assertEquals(book.getIsbn(), "Isbn");
        assertEquals(book.getPublisher(), "Publisher");
    }

    @Test
    void update() {
        var author = new Author();
        author.setId(3L);

        var book = bookDao.save(new Book("Title", "Isbn", "Publisher", author));
        book.setTitle("New title");
        var updatedBook = bookDao.update(book);
        assertEquals(updatedBook.getTitle(), "New title");
    }

    @Test
    void delete() {
        var author = new Author();
        author.setId(3L);

        var book = bookDao.save(new Book("Title", "Isbn", "Publisher", author));
        bookDao.delete(book.getId());
        assertNull(bookDao.getById(book.getId()));
    }
}
