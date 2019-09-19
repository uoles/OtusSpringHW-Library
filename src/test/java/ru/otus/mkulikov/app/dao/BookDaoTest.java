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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateBook.getBook;
import static ru.otus.mkulikov.generators.GenerateBook.getBooksList;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenre;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 13:16
 */

@DataMongoTest
@DisplayName("Класс BookDao")
class BookDaoTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";
    private final String ID_3 = "3";
    private final String ID_4 = "4";

    private final int OBJECT_COUNT_1 = 1;
    private final int OBJECT_COUNT_2 = 2;
    private final int OBJECT_COUNT_3 = 3;

    private final String DATE_TIME = "2019-01-01 10:01:01";

    private final String UPDATED_BOOK_NAME = "UpdatedBookName";
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
        bookDaoJpa.save(getBook(ID_3));
    }

    @Test
    @DisplayName("Получение книги по id")
    void getById() {
        Optional<Book> book = bookDaoJpa.findById(ID_1);

        assertThat(book).isNotEmpty();
        assertThat(book).contains(getBook(ID_1));
    }

    @Test
    @DisplayName("Получение книг по id автора")
    void getByAuthorId() {
        List<Book> books = bookDaoJpa.findByAuthor_Id(ID_1);

        assertThat(books).isNotEmpty();
        assertThat(books).size().isEqualTo(OBJECT_COUNT_1);
    }

    @Test
    @DisplayName("Получение книг по id жанра")
    void getByGenreId() {
        List<Book> books = bookDaoJpa.findByGenre_Id(ID_1);

        assertThat(books).isNotEmpty();
        assertThat(books).size().isEqualTo(OBJECT_COUNT_2);
    }

    @Test
    @DisplayName("Получение всех книг")
    void getAllObjects() {
        List<Book> books = bookDaoJpa.findAll();

        assertThat(books).size().isEqualTo(OBJECT_COUNT_3);
        assertThat(books).containsAll(getBooksList());
    }

    @Test
    @DisplayName("Добавление книги")
    void addObject() {
        Book book = getNewBook(ID_4);

        bookDaoJpa.save(book);
        Optional<Book> book_selected = bookDaoJpa.findById(ID_4);

        assertThat(book_selected).isNotEmpty();
        assertThat(book_selected).contains(book);
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteObject() {
        bookDaoJpa.deleteById(ID_1);
        Optional<Book> book = bookDaoJpa.findById(ID_1);
        assertThat(book).isEmpty();
    }

    @Test
    @DisplayName("Обновление книги")
    void updateObject() {
        Book book = getUpdatedBook();

        bookDaoJpa.save(book);
        Optional<Book> book_selected = bookDaoJpa.findById(ID_1);

        assertThat(book_selected).isNotEmpty();
        assertThat(book_selected).contains(book);
    }

    private Book getUpdatedBook() {
        Author author = authorDao.findById(ID_1).orElse(null);
        Genre genre = genreDao.findById(ID_1).orElse(null);
        return new Book(ID_1, new Date(), UPDATED_BOOK_NAME, author, genre, UPDATED_DESCRIPTION);
    }

    private Book getNewBook(String id) {
        Author author = authorDao.findById(ID_1).orElse(null);
        Genre genre = genreDao.findById(ID_1).orElse(null);
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        return new Book(id, date, UPDATED_BOOK_NAME, author, genre, UPDATED_DESCRIPTION);
    }
}