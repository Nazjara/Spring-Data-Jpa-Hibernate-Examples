package com.nazjara.dao;

import com.nazjara.model.Author;

public interface AuthorDao {
    Author getById(Long id);
    Author getByName(String firstName, String lastName);
    Author save(Author author);
    Author update(Author author);
    void delete(Long id);
}
