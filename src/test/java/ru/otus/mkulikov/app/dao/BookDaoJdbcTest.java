package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.AppTestConfig;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 13:16
 */

@DisplayName("Класс BookDaoJdbc")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(1L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                () -> assertEquals("book_1", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getAllObjects() {
        List<Book> books = bookDaoJdbc.getAllObjects();

        assertAll(
                "books",
                () -> assertNotNull(books),
                () -> assertEquals(3, books.size()),
                () -> assertEquals("book_1", books.get(0).getCaption()),
                () -> assertEquals("book_2", books.get(1).getCaption()),
                () -> assertEquals("book_3", books.get(2).getCaption())
        );
    }

    @Test
    void addObject() {
        int count = bookDaoJdbc.addObject(getNewBook());
        Book book = bookDaoJdbc.getById(4L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(1, count),
                () -> assertEquals(4L, book.getId()),
                () -> assertEquals("Test_Book", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("Test_Comment", book.getComment())
        );
    }

    @Test
    void deleteObject() {
        int count = bookDaoJdbc.deleteObject(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertThrows(EmptyResultDataAccessException.class, () -> { bookDaoJdbc.getById(1L); })
        );
    }

    @Test
    void updateObject() {
        Book book1 = bookDaoJdbc.getById(1L);
        int count = bookDaoJdbc.updateObject(getNewBook());
        Book book2 = bookDaoJdbc.getById(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertEquals("book_1", book1.getCaption()),
                () -> assertEquals("comment", book1.getComment()),
                () -> assertEquals("Test_Book", book2.getCaption()),
                () -> assertEquals("Test_Comment", book2.getComment())
        );
    }

    private Book getNewBook() {
        Author author = new Author(1L, "TestSurname", "TestFirstName", "TestSecondName");
        Genre genre = new Genre(1L, "Test4");
        return new Book(1L, new Date(), "Test_Book", author, genre, "Test_Comment");
    }
}