package ru.otus.mkulikov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbcOperations;

    public BookDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Book getById(int id) {
        return jdbcOperations.queryForObject("select * from Book where id = ? ", new Object[]{id}, new BookMapper());
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcOperations.query("select * from Book", new BookMapper());
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

    public static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("ID");
            Date add_record_date = resultSet.getDate("ADD_RECORD_DATE");
            String caption = resultSet.getString("CAPTION");
            int author_id = resultSet.getInt("AUTHOR_ID");
            int genre_id = resultSet.getInt("GENRE_ID");
            String comment = resultSet.getString("COMMENT");

            return new Book(id, add_record_date, caption, author_id, genre_id, comment);
        }
    }
}

