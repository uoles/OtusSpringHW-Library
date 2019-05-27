package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbcOperations;

    @Override
    public Author getById(int id) {
        return jdbcOperations.queryForObject("select * from Author where id = ? ", new Object[]{id}, new AuthorMapper());
    }

    @Override
    public List<Author> getAllObjects() {
        return jdbcOperations.query("select * from Author", new AuthorMapper());
    }

    @Override
    public int addObject(Book book) {
        return 0;
    }

    @Override
    public void deleteObject(Book book) {

    }

    @Override
    public int updateObject(Book book) {
        return 0;
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("ID");
            String surname = resultSet.getString("SURNAME");
            String first_name = resultSet.getString("FIRST_NAME");
            String second_name = resultSet.getString("SECOND_NAME");

            return new Author(id, surname, first_name, second_name);
        }
    }
}
