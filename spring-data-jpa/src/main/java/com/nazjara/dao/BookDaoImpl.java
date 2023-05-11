package com.nazjara.dao;

import com.nazjara.model.Book;
import com.nazjara.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository repository;

    public BookDaoImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Override
    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Book getByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book getByTitlePositionalParameterQuery(String title) {
        return repository.findByTitlePositionalParameterQuery(title);
    }

    @Override
    public Book getByTitleNamedParameterQuery(String title) {
        return repository.findByTitleNamedParameterQuery(title);
    }

    @Override
    public Book getByTitleNativeQuery(String title) {
        return repository.findByTitleNativeQuery(title);
    }

    @Override
    public Future<Book> getByTitleAsync(String title) {
        return repository.queryByTitle(title);
    }

    @Override
    public Stream<Book> getByTitleNotNull() {
        return repository.findAllByTitleNotNull();
    }

    @Override
    public List<Book> namedGetAll() {
        return repository.namedFindAll();
    }

    @Override
    public Book namedGetByTitle(String title) {
        return repository.namedFindByTitle(title);
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    @Transactional
    public Book update(Book book) {
       var foundBook = getById(book.getId());
       foundBook.setTitle(book.getTitle());
       foundBook.setIsbn(book.getIsbn());
       foundBook.setPublisher(book.getPublisher());
       return save(book);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
