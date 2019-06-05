package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao<Genre> {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Genre getById(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        return namedParameterJdbcOperations.queryForObject("select * from Genre where id = :id ", paramMap, new GenreMapper());
    }

    @Override
    public List<Genre> getAllObjects() {
        return namedParameterJdbcOperations.query("select * from Genre order by id", new GenreMapper());
    }

    @Override
    public int addObject(Genre genre) {
        return namedParameterJdbcOperations.update(
                "insert into Genre (name) values (:name)",
                new BeanPropertySqlParameterSource(genre));
    }

    @Override
    public int deleteObject(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        return namedParameterJdbcOperations.update("delete from Genre where id = :id ", paramMap);
    }

    @Override
    public int updateObject(Genre genre) {
        return namedParameterJdbcOperations.update(
                "update Genre set name = :name where id = :id ",
                new BeanPropertySqlParameterSource(genre));
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
