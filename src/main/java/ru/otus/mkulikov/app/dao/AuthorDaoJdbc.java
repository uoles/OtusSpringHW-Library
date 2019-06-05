package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Author getById(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        return namedParameterJdbcOperations.queryForObject("select * from Author where id = :id ", paramMap, new AuthorMapper());
    }

    @Override
    public List<Author> getAllObjects() {
        return namedParameterJdbcOperations.query("select * from Author order by id", new AuthorMapper());
    }

    @Override
    public int addObject(Author author) {
        return namedParameterJdbcOperations.update(
                "insert into Author (surname, first_name, second_name) values (:surname, :firstName, :secondName)",
                new BeanPropertySqlParameterSource(author)
        );
    }

    @Override
    public int deleteObject(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        return namedParameterJdbcOperations.update("delete from Author where id = :id ", paramMap);
    }

    @Override
    public int updateObject(Author author) {
        return namedParameterJdbcOperations.update(
                "update Author set surname = :surname, first_name = :firstName, second_name = :secondName where id = :id ",
                new BeanPropertySqlParameterSource(author)
        );
    }

    public static class AuthorMapper implements RowMapper<Author> {

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
