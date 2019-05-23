package ru.otus.mkulikov.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.model.Book;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbcOperations;

    public AuthorDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
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
}
