package com.nazjara.dao;

import com.nazjara.model.Author;
import org.junit.jupiter.api.Disabled;
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
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void getByIdWithBooks() {
        var author = authorDao.getByIdWithBooks(1L);
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
        assertEquals(author.getBooks().size(), 3);
    }

    @Test
    @Disabled
    void getByName() {
        var author = authorDao.getByName("Craig", "Walls");
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
    }

    @Test
    void save() {
        var author = authorDao.save(new Author("Name", "Surname"));
        assertNotNull(author.getId());
        assertEquals(author.getFirstName(), "Name");
        assertEquals(author.getLastName(), "Surname");
    }

    @Test
    void update() {
        var author = authorDao.save(new Author("Name", "Surname"));
        author.setFirstName("New first name");
        var updatedAuthor = authorDao.update(author);
        assertEquals(updatedAuthor.getFirstName(), "New first name");
    }

    @Test
    void delete() {
        var author = authorDao.save(new Author("Name", "Surname"));
        authorDao.delete(author.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getByName(author.getFirstName(), author.getLastName()));
    }
}
