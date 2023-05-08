package com.nazjara;

import com.nazjara.dao.AuthorDao;
import com.nazjara.model.Author;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
    void getById() {
        var author = authorDao.getById(1L);
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
    }

    @Test
    void getByName() {
        var author = authorDao.getByName("Craig", "Walls");
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
    }

    @Test
    void getByNameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> authorDao.getByName("Craig", "Wallss"));
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
        assertNull(authorDao.getById(author.getId()));
    }
}
