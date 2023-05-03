package com.nazjara.dao;

import com.nazjara.model.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource source;
    private final AuthorDao authorDao;

    public BookDaoImpl(DataSource source, AuthorDao authorDao) {
        this.source = source;
        this.authorDao = authorDao;
    }

    @Override
    public Book getById(Long id) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM book where id = ?")) {

            preparedStatement.setLong(1, id);

            var book = executeQuery(preparedStatement);
            if (book != null) return book;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book getByTitle(String title) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM book where title = ?")) {

            preparedStatement.setString(1, title);

            var book = executeQuery(preparedStatement);
            if (book != null) return book;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book save(Book book) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());

            if (book.getAuthor() != null) {
                preparedStatement.setLong(4, book.getAuthor().getId());
            } else {
                preparedStatement.setNull(4, -5);
            }


            preparedStatement.execute();

            try (var statement = connection.createStatement();
                 var resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (resultSet.next()) {
                    return this.getById(resultSet.getLong(1));
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book update(Book book) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?")) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());

            if (book.getAuthor() != null) {
                preparedStatement.setLong(4, book.getAuthor().getId());
            }

            preparedStatement.setLong(5, book.getId());

            preparedStatement.execute();

            return getById(book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book executeQuery(PreparedStatement preparedStatement) throws SQLException {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                var book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setAuthor(authorDao.getById(resultSet.getLong("author_id")));
                return book;
            }
        }
        return null;
    }
}
