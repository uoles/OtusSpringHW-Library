package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.AppTestConfig;
import ru.otus.mkulikov.app.model.Book;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private BookDaoJdbc bookDaoJdbc;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        bookDaoJdbc = new BookDaoJdbc(new JdbcTemplate(db));
    }

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(1);

        assertAll("book",
                  () -> assertNotNull(book),
                  () -> assertEquals(1, book.getId()),
                  () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                  () -> assertEquals("book_1", book.getCaption()),
                  () -> assertEquals(1, book.getAuthorId()),
                  () -> assertEquals(1, book.getGenreId()),
                  () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getAllObjects() {
        List<Book> books = bookDaoJdbc.getAllObjects();

        assertAll("books",
                  () -> assertNotNull(books),
                  () -> assertEquals(3, books.size()),
                  () -> assertEquals("book_1", books.get(0).getCaption()),
                  () -> assertEquals("book_2", books.get(1).getCaption()),
                  () -> assertEquals("book_3", books.get(2).getCaption())
        );
    }

    @Test
    void addObject() {
        int count = bookDaoJdbc.addObject(
                new Book("Test_Book",2,3,"Test_Comment")
        );

        Book book = bookDaoJdbc.getById(4);

        assertAll("book",
                  () -> assertNotNull(book),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, book.getId()),
                  () -> assertEquals("Test_Book", book.getCaption()),
                  () -> assertEquals(2, book.getAuthorId()),
                  () -> assertEquals(3, book.getGenreId()),
                  () -> assertEquals("Test_Comment", book.getComment())
        );
    }

    @Test
    void deleteObject() {
        int count = bookDaoJdbc.deleteObject(1);

        assertAll("book",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { bookDaoJdbc.getById(1); })
        );
    }

    @Test
    void updateObject() {
        Book book1 = bookDaoJdbc.getById(1);
        int count = bookDaoJdbc.updateObject(
                new Book(1, new Date(),"Test_Book",2,3,"Test_Comment")
        );
        Book book2 = bookDaoJdbc.getById(1);

        assertAll("book",
                  () -> assertEquals(1, count),
                  () -> assertEquals("book_1", book1.getCaption()),
                  () -> assertEquals("comment", book1.getComment()),
                  () -> assertEquals("Test_Book", book2.getCaption()),
                  () -> assertEquals("Test_Comment", book2.getComment())
        );
    }
}