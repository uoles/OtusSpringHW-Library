package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 13:16
 */

@DataMongoTest
@DisplayName("Класс BookDao")
class BookDaoTest {

    private final long ID_1 = 1L;
    private final long ID_2 = 2L;
    private final long ID_3 = 3L;
    private final long ID_4 = 4L;

    private final int OBJECT_COUNT_3 = 3;
    private final int OBJECT_COUNT_2 = 2;

    private final String SURNAME = "Surname";
    private final String FIRST_NAME = "FirstName";
    private final String SECOND_NAME = "SecondName";

    private final String TEST_SURNAME = "TestSurname";
    private final String TEST_FIRST_NAME = "TestFirstName";
    private final String TEST_SECOND_NAME = "TestSecondName";

    private final String GENRE_NAME = "GenreName";
    private final String DATE_TIME = "2019-01-01 10:01:01";

    private final String BOOK_NAME = "BookName";
    private final String UPDATED_BOOK_NAME = "UpdatedBookName";
    private final String DESCRIPTION = "Description";
    private final String UPDATED_DESCRIPTION = "UpdatedDescription";

    @Autowired
    private BookDao bookDaoJpa;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @BeforeEach
    void init() {
        authorDao.save(getAuthor(ID_1));
        authorDao.save(getAuthor(ID_2));
        authorDao.save(getAuthor(ID_3));

        genreDao.save(getGenre(ID_1));
        genreDao.save(getGenre(ID_2));
        genreDao.save(getGenre(ID_3));

        bookDaoJpa.save(getBook(ID_1));
        bookDaoJpa.save(getBook(ID_2));
    }

    @Test
    @DisplayName("Получение книги по id")
    void getById() {
        Optional<Book> book = bookDaoJpa.findById(ID_1);

        assertThat(book).isNotEmpty();
        assertThat(book).contains(getBook(ID_1));
    }

    @Test
    @DisplayName("Получение всех книг")
    void getAllObjects() {
        List<Book> books = bookDaoJpa.findAll();

        assertThat(books).size().isEqualTo(OBJECT_COUNT_2);
        assertThat(books).containsAll(getBooks());
    }

    @Test
    @DisplayName("Добавление книги")
    void addObject() {
        bookDaoJpa.save(getBook(ID_4));
        Optional<Book> book = bookDaoJpa.findById(ID_4);



//        assertAll(
//                "book",
//                () -> assertNotNull(book),
//                () -> assertEquals(4L, book.getId()),
//                () -> assertEquals("Test_Book", book.getCaption()),
//                () -> assertEquals(1, book.getAuthor().getId()),
//                () -> assertEquals(1, book.getGenre().getId()),
//                () -> assertEquals("Test_Description", book.getDescription())
//        );
    }
//
//    @Test
//    @DisplayName("Удаление книги по id")
//    void deleteObject() {
//        bookDaoJpa.deleteById(1L);
//        Optional<Book> book = bookDaoJpa.findById(1L);
//        assertThat(book).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Обновление книги")
//    void updateObject() {
//        bookDaoJpa.save(getUpdatedBook());
//        Book book2 = bookDaoJpa.findById(1L).orElse(null);
//
//        assertAll(
//                "book",
//                () -> assertEquals("Test_Book", book2.getCaption()),
//                () -> assertEquals("Test_Description", book2.getDescription())
//        );
//    }

    private Book getBook(long id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        return new Book(id, date, BOOK_NAME, author, genre, DESCRIPTION);
    }

    private Author getAuthor(long id) {
        return new Author(id, SURNAME + id, FIRST_NAME + id, SECOND_NAME + id);
    }

    private Genre getGenre(long id) {
        return new Genre(id, GENRE_NAME + id);
    }

    private List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        books.add(getBook(ID_1));
        books.add(getBook(ID_2));
        return books;
    }

    private Book getUpdatedBook() {
        Author author = authorDao.findById(1L).orElse(null);
        Genre genre = genreDao.findById(1L).orElse(null);
        return new Book(1L, new Date(), UPDATED_BOOK_NAME, author, genre, UPDATED_DESCRIPTION);
    }
}