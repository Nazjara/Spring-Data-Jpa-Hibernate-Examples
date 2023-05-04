package com.nazjara.dao;

import com.nazjara.model.Author;
import com.nazjara.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

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
        assertEquals(book.getAuthor().getId(), 1L);
    }

    @Test
    void getByTitle() {
        var book = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
        assertEquals(book.getAuthor().getId(), 1L);
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
        assertEquals(book.getAuthor().getId(), 3L);
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
        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.getById(book.getId()));
    }
}
