package com.nazjara.dao;

import com.nazjara.model.Book;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookDao {
    Book getById(Long id);
    Book getByTitle(String title);
    Book getByTitlePositionalParameterQuery(String title);
    Book getByTitleNamedParameterQuery(String title);
    Book getByTitleNativeQuery(String title);
    Future<Book> getByTitleAsync(String title);
    Stream<Book> getByTitleNotNull();
    List<Book> namedGetAll();
    Book namedGetByTitle(String title);
    Book save(Book book);
    Book update(Book book);
    void delete(Long id);
}
