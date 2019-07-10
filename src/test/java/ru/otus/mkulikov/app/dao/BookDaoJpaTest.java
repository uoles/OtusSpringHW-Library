package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 13:16
 */

@DataJpaTest
@DisplayName("Класс BookDaoJpa")
class BookDaoJpaTest {

    @Autowired
    private BookDao bookDaoJpa;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Test
	@DisplayName("Получение книги по id")
    void getById() {
        Book book = bookDaoJpa.getById(1L).orElse(null);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("2019-01-01", DateUtil.dateToString(book.getAddRecordDate())),
                () -> assertEquals("book_1", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("description", book.getDescription())
        );
    }

    @Test
	@DisplayName("Получение всех книг")
    void getAllObjects() {
        List<Book> books = bookDaoJpa.getAllObjects();

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
    @DisplayName("Добавление книги")
    void addObject() {
        bookDaoJpa.save(getNewBook());
        Book book = bookDaoJpa.getById(4L).orElse(null);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(4L, book.getId()),
                () -> assertEquals("Test_Book", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("Test_Description", book.getDescription())
        );
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteObject() {
        bookDaoJpa.deleteById(1L);
        Optional<Book> book = bookDaoJpa.getById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    @DisplayName("Обновление книги")
    void updateObject() {
        bookDaoJpa.save(getUpdatedBook());
        Book book2 = bookDaoJpa.getById(1L).orElse(null);

        assertAll(
                "book",
                () -> assertEquals("Test_Book", book2.getCaption()),
                () -> assertEquals("Test_Description", book2.getDescription())
        );
    }

    private Book getNewBook() {
        Author author = authorDao.findById(1L).orElse(null);
        Genre genre = genreDao.findById(1L).orElse(null);
        return new Book("Test_Book", author, genre, "Test_Description");
    }

    private Book getUpdatedBook() {
        Author author = authorDao.findById(1L).orElse(null);
        Genre genre = genreDao.findById(1L).orElse(null);
        return new Book(1L, new Date(), "Test_Book", author, genre, "Test_Description");
    }
}