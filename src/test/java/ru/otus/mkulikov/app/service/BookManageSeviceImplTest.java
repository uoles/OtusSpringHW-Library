package ru.otus.mkulikov.app.service;

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
import ru.otus.mkulikov.app.dao.AuthorDaoJdbc;
import ru.otus.mkulikov.app.dao.BookDaoJdbc;
import ru.otus.mkulikov.app.dao.GenreDaoJdbc;
import ru.otus.mkulikov.app.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private BookManageSevice booksManageSevice;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        AuthorDaoJdbc authorDaoJdbc = new AuthorDaoJdbc(new JdbcTemplate(db));
        BookDaoJdbc bookDaoJdbc = new BookDaoJdbc(new JdbcTemplate(db));
        GenreDaoJdbc genreDaoJdbc = new GenreDaoJdbc(new JdbcTemplate(db));

        booksManageSevice = new BookManageSeviceImpl(authorDaoJdbc, bookDaoJdbc, genreDaoJdbc);
    }

    @Test
    void getBookById() {
        Book book = booksManageSevice.getBookById(1);

        assertAll("book",
                  () -> assertNotNull(book),
                  () -> assertNull(book.getAuthor()),
                  () -> assertNull(book.getGenre()),
                  () -> assertEquals(1, book.getId()),
                  () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                  () -> assertEquals("book_1", book.getCaption()),
                  () -> assertEquals(1, book.getAuthorId()),
                  () -> assertEquals(1, book.getGenreId()),
                  () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getFullBookById() {
        Book book = booksManageSevice.getFullBookById(1);

        assertAll("book",
                  () -> assertNotNull(book),
                  () -> assertNotNull(book.getAuthor()),
                  () -> assertNotNull(book.getGenre()),
                  () -> assertEquals(1, book.getId()),
                  () -> assertEquals("2019-01-01", book.getAddRecordDateString()),
                  () -> assertEquals("book_1", book.getCaption()),
                  () -> assertEquals(1, book.getAuthorId()),
                  () -> assertEquals(1, book.getGenreId()),
                  () -> assertEquals("comment", book.getComment())
        );
    }

    @Test
    void getBooks() {
        List<Book> books = booksManageSevice.getBooks();

        assertAll("books",
                  () -> assertNotNull(books),
                  () -> assertEquals(3, books.size()),
                  () -> assertEquals("book_1", books.get(0).getCaption()),
                  () -> assertEquals("book_2", books.get(1).getCaption()),
                  () -> assertEquals("book_3", books.get(2).getCaption())
        );
    }

    @Test
    void addBook() {
        int count = booksManageSevice.addBook("Test_Book",2,3,"Test_Comment");
        Book book = booksManageSevice.getBookById(4);

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
    void updateBook() {
        Book book1 = booksManageSevice.getBookById(1);
        int count = booksManageSevice.updateBook(1, "Test_Book", 2, 3, "Test_Comment");
        Book book2 = booksManageSevice.getBookById(1);

        assertAll("book",
                  () -> assertEquals(1, count),
                  () -> assertEquals("book_1", book1.getCaption()),
                  () -> assertEquals("comment", book1.getComment()),
                  () -> assertEquals("Test_Book", book2.getCaption()),
                  () -> assertEquals("Test_Comment", book2.getComment())
        );
    }

    @Test
    void deleteBook() {
        int count = booksManageSevice.deleteBook(1);

        assertAll("book",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { booksManageSevice.getBookById(1); })
        );
    }

}