package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 13:16
 */

@DisplayName("Класс BookDaoJpa")
@RunWith(SpringRunner.class)
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
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
        Book book = bookDaoJpa.getById(1L);

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
        int count = bookDaoJpa.save(getNewBook());
        Book book = bookDaoJpa.getById(4L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(1, count),
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
        int count = bookDaoJpa.deleteObject(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> { bookDaoJpa.getById(1L); })
        );
    }

    @Test
    @DisplayName("Обновление книги")
    void updateObject() {
        Book book1 = bookDaoJpa.getById(1L);
        int count = bookDaoJpa.save(getUpdatedBook());
        Book book2 = bookDaoJpa.getById(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertEquals("book_1", book1.getCaption()),
                () -> assertEquals("description", book1.getDescription()),
                () -> assertEquals("Test_Book", book2.getCaption()),
                () -> assertEquals("Test_Description", book2.getDescription())
        );
    }

    private Book getNewBook() {
        Author author = authorDao.findById(1L).get();
        Genre genre = genreDao.findById(1L).get();
        return new Book("Test_Book", author, genre, "Test_Description");
    }

    private Book getUpdatedBook() {
        Author author = authorDao.findById(1L).get();
        Genre genre = genreDao.findById(1L).get();
        return new Book(1L,"Test_Book", author, genre, "Test_Description");
    }
}