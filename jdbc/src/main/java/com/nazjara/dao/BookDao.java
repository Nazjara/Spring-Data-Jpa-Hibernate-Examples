package com.nazjara.dao;

import com.nazjara.model.Book;

public interface BookDao {
    Book getById(Long id);
    Book getByTitle(String title);
    Book save(Book book);
    Book update(Book book);
    void delete(Long id);
}
