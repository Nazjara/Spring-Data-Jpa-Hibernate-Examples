package com.nazjara.dao;

import com.nazjara.model.Author;
import com.nazjara.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final BookMapper bookMapper;
    private final JdbcTemplate jdbcTemplate;
    private final AuthorDao authorDao;

    public BookDaoImpl(JdbcTemplate jdbcTemplate, AuthorDao authorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorDao = authorDao;
        this.bookMapper = new BookMapper();
    }

    @Override
    public List<Book> findAll(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM book LIMIT ? OFFSET ?", bookMapper, pageSize, offset);
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM book LIMIT ? OFFSET ?", bookMapper, pageable.getPageSize(),
                pageable.getOffset());
    }

    @Override
    public List<Book> findAllSortedByTitle(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY title LIMIT ? OFFSET ?",
                bookMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", bookMapper, id);
    }

    @Override
    public Book getByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?",
                bookMapper, title);
    }

    @Override
    public Book save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                book.getTitle(), book.getIsbn(), book.getPublisher(),
                book.getAuthor() == null ? null : book.getAuthor().getId());

        return getById(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
    }

    @Override
    public Book update(Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(),
                book.getAuthor() == null ? null : book.getAuthor().getId(), book.getId());

        return getById(book.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = null;

            try {
                if (rs.getLong("author_id") != 0) {
                    author = authorDao.getByIdWithBooks(rs.getLong("author_id"));
                }
            } catch (EmptyResultDataAccessException e) {
                // ignore
            }

            var book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setIsbn(rs.getString("isbn"));
            book.setPublisher(rs.getString("publisher"));
            book.setAuthor(author);

            return book;
        }
    }
}
