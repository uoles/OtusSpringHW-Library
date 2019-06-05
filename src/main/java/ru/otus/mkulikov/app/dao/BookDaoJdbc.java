package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao<Book> {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final String BOOK_SELECT =
            "select b.*, a.surname, a.first_name, a.second_name, g.name "
            + "from Book b "
            + "inner join Author a on a.id = b.author_id "
            + "inner join Genre g on g.id = b.genre_id "
            + "where b.id = :id ";

    private final String BOOK_SELECT_ALL =
            "select b.*, a.surname, a.first_name, a.second_name, g.name "
            + "from Book b "
            + "inner join Author a on a.id = b.author_id "
            + "inner join Genre g on g.id = b.genre_id "
            + "order by b.id ";

    private final String BOOK_UPDATE =
            "update Book "
            + "set add_record_date = sysdate, caption = :caption, author_id = :author_id, genre_id = :genre_id, comment = :comment "
            + "where id = :id ";

    private final String BOOK_INSERT =
            "insert into Book (add_record_date, caption, author_id, genre_id, comment) "
            + " values (sysdate, :caption, :author_id, :genre_id, :comment)";

    private final String BOOK_DELETE = "delete from Book where id = :id ";

    @Override
    public Book getById(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        List<Book> books = namedParameterJdbcOperations.query(BOOK_SELECT, paramMap, new BookMapper());
        return (books != null) ? books.get(0) : null;
    }

    @Override
    public List<Book> getAllObjects() {
        return namedParameterJdbcOperations.query(BOOK_SELECT_ALL, new BookMapper());
    }

    @Override
    public int addObject(Book book) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("caption", book.getCaption());
        paramMap.put("author_id", book.getAuthor().getId());
        paramMap.put("genre_id", book.getGenre().getId());
        paramMap.put("comment", book.getComment());

        return namedParameterJdbcOperations.update(BOOK_INSERT, paramMap);
    }

    @Override
    public int deleteObject(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);

        return namedParameterJdbcOperations.update(BOOK_DELETE, paramMap);
    }

    @Override
    public int updateObject(Book book) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("caption", book.getCaption());
        paramMap.put("author_id", book.getAuthor().getId());
        paramMap.put("genre_id", book.getGenre().getId());
        paramMap.put("comment", book.getComment());
        paramMap.put("id", book.getId());

        return namedParameterJdbcOperations.update(BOOK_UPDATE, paramMap);
    }

    public static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            Date addRecordDate = resultSet.getDate("add_record_date");
            String caption = resultSet.getString("caption");
            String comment = resultSet.getString("comment");

            Author author = new Author(
                    resultSet.getLong("author_id"),
                    resultSet.getString("surname"),
                    resultSet.getString("first_name"),
                    resultSet.getString("second_name")
            );

            Genre genre = new Genre(
                    resultSet.getLong("genre_id"),
                    resultSet.getString("name")
            );

            return new Book(id, addRecordDate, caption, author, genre, comment);
        }
    }
}

