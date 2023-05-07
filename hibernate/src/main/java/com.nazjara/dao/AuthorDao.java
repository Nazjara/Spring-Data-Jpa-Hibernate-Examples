package com.nazjara.dao;

import com.nazjara.model.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);
    Author getByName(String firstName, String lastName);
    Author getByNameCriteria(String firstName, String lastName);
    Author getByNameNative(String firstName, String lastName);
    List<Author> getAll();
    List<Author> listByLastNameLike(String lastName);
    Author save(Author author);
    Author update(Author author);
    void delete(Long id);
}
