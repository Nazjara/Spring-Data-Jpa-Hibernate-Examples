package com.nazjara.dao;

import com.nazjara.model.Author;
import com.nazjara.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

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
    void getAllPageable() {
        var books1 = bookDao.findAll(PageRequest.of(0, 2));
        var books2 = bookDao.findAll(PageRequest.of(1, 2));
        var books3 = bookDao.findAll(PageRequest.of(100, 2));
        assertNotNull(books1);
        assertNotNull(books2);
        assertNotNull(books3);
        assertEquals(2, books1.size());
        assertEquals(2, books2.size());
        assertTrue(books3.isEmpty());
        assertNotEquals(books1, books2);
    }

    @Test
    void getAllSortedByTitle() {
        var books = bookDao.findAllSortedByTitle(PageRequest.of(0, 2));
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

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
    void getByTitleCriteria() {
        var book = bookDao.getByTitleCriteria("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByTitleNative() {
        var book = bookDao.getByTitleNative("Spring in Action, 5th Edition");
        assertEquals(book.getId(), 1L);
        assertEquals(book.getTitle(), "Spring in Action, 5th Edition");
        assertEquals(book.getIsbn(), "978-1617294945");
        assertEquals(book.getPublisher(), "Simon & Schuster");
    }

    @Test
    void getByIsbn() {
        var book = bookDao.getByIsbn("978-1617294945");
        assertEquals(book.getIsbn(), "978-1617294945");
    }

    @Test
    void getAll2() {
        var book = bookDao.getAll();
        assertThat(book.size(), greaterThan(0));
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
        assertNull(bookDao.getById(book.getId()));
    }
}
