package ru.otus.mkulikov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.model.Book;
import ru.otus.mkulikov.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbcOperations;

    public GenreDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Genre getById(int id) {
        return jdbcOperations.queryForObject("select * from Genre where id = ? ", new Object[]{id}, new GenreMapper());
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

    public static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");

            return new Genre(id, name);
        }
    }
}
