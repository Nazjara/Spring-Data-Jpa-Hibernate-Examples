package com.nazjara.dao;

import com.nazjara.model.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorDao {
    List<Author> findAllByLastNameSortedByFirstName(String lastName, Pageable pageable);
    Author getById(Long id);
    Author getByName(String firstName, String lastName);
    Author save(Author author);
    Author update(Author author);
    void delete(Long id);
}
