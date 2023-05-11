package com.nazjara.dao;

import com.nazjara.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {
    List<Book> findAll(Pageable pageable);
    List<Book> findAllSortedByTitle(Pageable pageable);
    Book getById(Long id);
    Book getByTitle(String title);
    Book getByTitleCriteria(String title);
    Book getByTitleNative(String title);
    Book getByIsbn(String isbn);
    List<Book> getAll();
    Book save(Book book);
    Book update(Book book);
    void delete(Long id);
}
