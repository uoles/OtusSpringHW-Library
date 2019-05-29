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
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 13:40
 */

@DisplayName("Класс BooksManageSevice")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class BooksManageSeviceImplTest {

    private BooksManageSevice booksManageSevice;

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

        booksManageSevice = new BooksManageSeviceImpl(authorDaoJdbc, bookDaoJdbc, genreDaoJdbc);
    }

    @Test
    void getBookById() {
        Book book = booksManageSevice.getBookById(1);

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
    void getAuthorById() {
        Author author = booksManageSevice.getAuthorById(1);

        assertAll("author",
                  () -> assertNotNull(author),
                  () -> assertEquals(1, author.getId()),
                  () -> assertEquals("Surname1", author.getSurname()),
                  () -> assertEquals("FirstName1", author.getFirstName()),
                  () -> assertEquals("SecondName1", author.getSecondName())
        );
    }

    @Test
    void getAuthors() {
        List<Author> authors = booksManageSevice.getAuthors();

        assertAll("authors",
                  () -> assertNotNull(authors),
                  () -> assertEquals(3, authors.size()),
                  () -> assertEquals("Surname1", authors.get(0).getSurname()),
                  () -> assertEquals("Surname2", authors.get(1).getSurname()),
                  () -> assertEquals("Surname3", authors.get(2).getSurname())
        );
    }

    @Test
    void getGenreById() {
        Genre genre = booksManageSevice.getGenreById(1);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(1, genre.getId()),
                  () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    void getGenres() {
        List<Genre> genres = booksManageSevice.getGenres();

        assertAll("genres",
                  () -> assertNotNull(genres),
                  () -> assertEquals(3, genres.size()),
                  () -> assertEquals("Genre1", genres.get(0).getName()),
                  () -> assertEquals("Genre2", genres.get(1).getName()),
                  () -> assertEquals("Genre3", genres.get(2).getName())
        );
    }

    @Test
    void addGenre() {
        int count = booksManageSevice.addGenre("Test4");
        Genre genre = booksManageSevice.getGenreById(4);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, genre.getId()),
                  () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    void updateGenre() {
        Genre genre1 = booksManageSevice.getGenreById(1);
        int count = booksManageSevice.updateGenre(1, "UpdatedName");
        Genre genre2 = booksManageSevice.getGenreById(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertEquals("Genre1", genre1.getName()),
                  () -> assertEquals("UpdatedName", genre2.getName())
        );
    }

    @Test
    void deleteGenre() {
        int count = booksManageSevice.deleteGenre(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { booksManageSevice.getGenreById(1); })
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

    @Test
    void addAuthor() {
        int count = booksManageSevice.addAuthor("TestSurname", "TestFirstName", "TestSecondName");
        Author author_selected = booksManageSevice.getAuthorById(4);

        assertAll("author",
                  () -> assertNotNull(author_selected),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, author_selected.getId()),
                  () -> assertEquals("TestSurname", author_selected.getSurname()),
                  () -> assertEquals("TestFirstName", author_selected.getFirstName()),
                  () -> assertEquals("TestSecondName", author_selected.getSecondName())
        );
    }

    @Test
    void updateAuthor() {
        Author author1 = booksManageSevice.getAuthorById(1);
        int count = booksManageSevice.updateAuthor(1,"TestSurname", "TestFirstName", "TestSecondName");
        Author author2 = booksManageSevice.getAuthorById(1);

        assertAll("author",
                  () -> assertEquals(1, count),
                  () -> assertEquals("Surname1", author1.getSurname()),
                  () -> assertEquals("FirstName1", author1.getFirstName()),
                  () -> assertEquals("SecondName1", author1.getSecondName()),
                  () -> assertEquals("TestSurname", author2.getSurname()),
                  () -> assertEquals("TestFirstName", author2.getFirstName()),
                  () -> assertEquals("TestSecondName", author2.getSecondName())
        );
    }

    @Test
    void deleteAuthor() {
        int count = booksManageSevice.deleteAuthor(1);

        assertAll("author",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { booksManageSevice.getAuthorById(1); })
        );
    }
}