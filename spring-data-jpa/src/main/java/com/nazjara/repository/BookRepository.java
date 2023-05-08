package com.nazjara.repository;

import com.nazjara.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Stream<Book> findAllByTitleNotNull();
    List<Book> namedFindAll();
    Book namedFindByTitle(@Param("title") String title);

    @Async
    Future<Book> queryByTitle(String title);

    @Query("SELECT b from Book b WHERE b.title = ?1")
    Book findByTitlePositionalParameterQuery(String title);

    @Query("SELECT b from Book b WHERE b.title = :title")
    Book findByTitleNamedParameterQuery(@Param("title") String title);

    @Query(value = "SELECT * from book WHERE title = :title", nativeQuery = true)
    Book findByTitleNativeQuery(@Param("title") String title);
}