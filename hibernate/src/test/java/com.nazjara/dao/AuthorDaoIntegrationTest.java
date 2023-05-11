package com.nazjara.dao;

import com.nazjara.model.Author;
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
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void getAllByLastNameSortedByFirstName() {
        var authors = authorDao.findAllByLastNameSortedByFirstName("Walls", PageRequest.of(0, 2));
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
    }

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
    void getByNameCriteria() {
        var author = authorDao.getByNameCriteria("Craig", "Walls");
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
    }

    @Test
    void getByNameNative() {
        var author = authorDao.getByNameNative("Craig", "Walls");
        assertEquals(author.getId(), 1L);
        assertEquals(author.getFirstName(), "Craig");
        assertEquals(author.getLastName(), "Walls");
    }

    @Test
    void getAll() {
        var author = authorDao.getAll();
        assertThat(author.size(), greaterThan(0));
    }

    @Test
    void listByLastNameLike() {
        var authors = authorDao.listByLastNameLike("Walls");
        assertThat(authors.size(), greaterThan(0));
        assertEquals(authors.get(0).getLastName(), "Walls");
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
