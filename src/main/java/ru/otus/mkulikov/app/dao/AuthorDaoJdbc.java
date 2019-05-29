package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Author;

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
public class AuthorDaoJdbc implements AuthorDao<Author> {

    private final JdbcOperations jdbcOperations;

    @Override
    public Author getById(int id) {
        return jdbcOperations.queryForObject("select * from Author where id = ? ", new Object[]{id}, new AuthorMapper());
    }

    @Override
    public List<Author> getAllObjects() {
        return jdbcOperations.query("select * from Author order by id", new AuthorMapper());
    }

    @Override
    public int addObject(Author author) {
        return jdbcOperations.update(
                "insert into Author (surname, first_name, second_name) values (?,?,?)",
                new Object[]{author.getSurname(), author.getFirstName(), author.getSecondName()}
        );
    }

    @Override
    public int deleteObject(int id) {
        return jdbcOperations.update("delete from Author where id = ? ", new Object[]{id});
    }

    @Override
    public int updateObject(Author author) {
        return jdbcOperations.update(
                "update Author set surname = ?, first_name = ?, second_name = ? where id = ? ",
                new Object[]{author.getSurname(), author.getFirstName(), author.getSecondName(), author.getId()}
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("ID");
            String surname = resultSet.getString("SURNAME");
            String firstName = resultSet.getString("FIRST_NAME");
            String secondName = resultSet.getString("SECOND_NAME");

            return new Author(id, surname, firstName, secondName);
        }
    }
}
