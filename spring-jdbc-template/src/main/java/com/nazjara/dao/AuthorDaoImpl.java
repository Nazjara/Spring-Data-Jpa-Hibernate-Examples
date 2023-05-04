package com.nazjara.dao;

import com.nazjara.model.Author;
import com.nazjara.model.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorMapper authorMapper;
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorMapper = new AuthorMapper();
    }

    @Override
    public Author getByIdWithBooks(Long id) {
        var sql = "SELECT author.id AS id, first_name, last_name, book.id as book_id, book.title, book.isbn, " +
                "book.publisher from author left outer join book on author.id = book.author_id where author.id = ?";

        return jdbcTemplate.query(sql, new AuthorExtractor(), id);
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? AND last_name = ?",
                authorMapper, firstName, lastName);
    }

    @Override
    public Author save(Author author) {
        jdbcTemplate.update("INSERT INTO author (first_name, last_name) VALUES (?, ?)",
                author.getFirstName(), author.getLastName());

        return getByIdWithBooks(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
    }

    @Override
    public Author update(Author author) {
        jdbcTemplate.update("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?",
                author.getFirstName(), author.getLastName(), author.getId());

        return getByIdWithBooks(author.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    private class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            var author = new Author();
            author.setId(rs.getLong("id"));
            author.setFirstName(rs.getString("first_name"));
            author.setLastName(rs.getString("last_name"));

            try {
                // check if books are in there
                rs.getString("title");
                author.setBooks(new ArrayList<>());
                author.getBooks().add(mapBook(rs));

                while (rs.next()) {
                    author.getBooks().add(mapBook(rs));
                }
            } catch (SQLException e) {
                //ignore
            }

            return author;
        }

        private Book mapBook(ResultSet rs) throws SQLException {
            var book = new Book();
            book.setId(rs.getLong(4));
            book.setTitle(rs.getString(5));
            book.setIsbn(rs.getString(6));
            book.setPublisher(rs.getString(7));

            return book;
        }
    }

    private class AuthorExtractor implements ResultSetExtractor<Author> {

        @Override
        public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
            rs.next();
            return new AuthorMapper().mapRow(rs, 0);
        }
    }
}
