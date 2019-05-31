package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.AppTestConfig;
import ru.otus.mkulikov.app.dao.AuthorDaoJdbc;
import ru.otus.mkulikov.app.dao.BookDaoJdbc;
import ru.otus.mkulikov.app.dao.GenreDaoJdbc;
import ru.otus.mkulikov.app.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 13:40
 */

@DisplayName("Класс BookManageSevice")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class BookManageSeviceImplTest {

    @Autowired
    private BookManageSevice booksManageSevice;

    @Test
    void getBookById() {
        Book book = booksManageSevice.getBookById(1L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertNull(book.getAuthor()),
                () -> assertNull(book.getGenre()),
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                () -> assertEquals("book_1", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getFullBookById() {
        Book book = booksManageSevice.getFullBookById(1);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertNotNull(book.getAuthor()),
                () -> assertNotNull(book.getGenre()),
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                () -> assertEquals("book_1", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getBooks() {
        List<Book> books = booksManageSevice.getBooks();

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
    void addBook() {
        int count = booksManageSevice.addBook("Test_Book", 2, 3, "Test_Comment");
        Book book = booksManageSevice.getBookById(4L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(1, count),
                () -> assertEquals(4L, book.getId()),
                () -> assertEquals("Test_Book", book.getCaption()),
                () -> assertEquals(2, book.getAuthor().getId()),
                () -> assertEquals(3, book.getGenre().getId()),
                () -> assertEquals("Test_Comment", book.getComment())
        );
    }

    @Test
    void updateBook() {
        Book book1 = booksManageSevice.getBookById(1L);
        int count = booksManageSevice.updateBook(1L, "Test_Book", 2, 3, "Test_Comment");
        Book book2 = booksManageSevice.getBookById(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertEquals("book_1", book1.getCaption()),
                () -> assertEquals("comment", book1.getComment()),
                () -> assertEquals("Test_Book", book2.getCaption()),
                () -> assertEquals("Test_Comment", book2.getComment())
        );
    }

    @Test
    void deleteBook() {
        int count = booksManageSevice.deleteBook(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertThrows(EmptyResultDataAccessException.class, () -> { booksManageSevice.getBookById(1L); })
        );
    }

}