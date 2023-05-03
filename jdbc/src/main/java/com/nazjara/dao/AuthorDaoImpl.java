package com.nazjara.dao;

import com.nazjara.model.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource source;

    public AuthorDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Author getById(Long id) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM author where id = ?")) {

            preparedStatement.setLong(1, id);

            var author = executeQuery(preparedStatement);
            if (author != null) return author;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM author where first_name = ? and last_name = ?")) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            var author = executeQuery(preparedStatement);
            if (author != null) return author;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Author save(Author author) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) VALUES (?, ?)")) {

            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());

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
    public Author update(Author author) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?")) {

            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());

            preparedStatement.execute();

            return getById(author.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        try (var connection = source.getConnection();
             var preparedStatement = connection.prepareStatement("DELETE FROM author WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Author executeQuery(PreparedStatement preparedStatement) throws SQLException {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                var author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
                return author;
            }
        }
        return null;
    }
}
