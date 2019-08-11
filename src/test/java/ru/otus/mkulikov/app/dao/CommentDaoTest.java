package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DataMongoTest
@DisplayName("Класс CommentDao")
class CommentDaoTest {

    private final long ID_1 = 1L;
    private final long ID_2 = 2L;
    private final long ID_3 = 3L;
    private final long ID_4 = 4L;

    private final int OBJECT_COUNT_4 = 4;
    private final int OBJECT_COUNT_3 = 3;
    private final int OBJECT_COUNT_2 = 2;

    private final String TEST_DATE_TIME = "2019-01-01 10:01:01";
    private final String GENRE_NAME = "GenreName";

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private BookDao bookDaoJpa;

    @Autowired
    private CommentDao commentDaoJpa;

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

        commentDaoJpa.save(getComment(ID_1, ID_1));
        commentDaoJpa.save(getComment(ID_2, ID_1));
        commentDaoJpa.save(getComment(ID_3, ID_2));
        commentDaoJpa.save(getComment(ID_4, ID_2));
    }

    @Test
    @DisplayName("Получение комментария по id")
    void getById() {
        Optional<Comment> comment = commentDaoJpa.findById(ID_1);

        assertThat(comment).isNotEmpty();
        assertThat(comment).contains(getComment(ID_1, ID_1));
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getAllObjects() {
        List<Comment> comments = commentDaoJpa.findAll();

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(OBJECT_COUNT_4);
        assertThat(comments).containsAll(getComments());
    }

    @Test
    @DisplayName("Получение всех комментариев для книги")
    void getObjectsByBook() {
        Optional<Book> book = bookDaoJpa.findById(ID_1);
        List<Comment> comments = commentDaoJpa.findByBook(book.orElse(null));

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(OBJECT_COUNT_2);
        assertThat(comments).contains(getComment(ID_1, ID_1), getComment(ID_2, ID_1));
    }

    @Test
    @DisplayName("Добавление комментария")
    void addObject() {
        Date date = new Date();
        Book book = bookDaoJpa.findById(ID_1).orElse(null);
        commentDaoJpa.save(new Comment(book, date, "user5", "text5"));

        Comment comment = commentDaoJpa.findById(5L).orElse(null);

        assertAll(
                "comment",
                () -> assertNotNull(comment),
                () -> assertNotNull(comment.getBook()),
                () -> assertEquals(5L, comment.getId()),
                () -> assertEquals(date, comment.getAddRecordDate()),
                () -> assertEquals("user5", comment.getUserName()),
                () -> assertEquals("text5", comment.getText())
        );
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteObject() {
        commentDaoJpa.deleteById(ID_1);
        Optional<Comment> comment = commentDaoJpa.findById(ID_1);
        assertThat(comment).isNotEmpty();
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateObject() {
        Comment comment1 = commentDaoJpa.findById(ID_1).orElse(null);

        Date date = new Date();
        commentDaoJpa.save(new Comment(ID_1, comment1.getBook(), date, "TestUser", "TestText"));

        Comment comment2 = commentDaoJpa.findById(ID_1).orElse(null);

        assertAll(
                "comment",
                () -> assertNotNull(comment1),
                () -> assertNotNull(comment2),
                () -> assertNotNull(comment1.getBook()),
                () -> assertNotNull(comment2.getBook()),
                () -> assertEquals("user1", comment1.getUserName()),
                () -> assertEquals("text1", comment1.getText()),
                () -> assertEquals(date, comment2.getAddRecordDate()),
                () -> assertEquals("TestUser", comment2.getUserName()),
                () -> assertEquals("TestText", comment2.getText())
        );
    }

    private Comment getComment(long id, long bookId) {
        Date date = DateUtil.stringToDateTime(TEST_DATE_TIME);
        Book book = getBook(bookId);
        return new Comment(id, book, date, "user" + id, "text" + id);
    }

    private Book getBook(long id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        Date date = DateUtil.stringToDateTime(TEST_DATE_TIME);
        return new Book(id, date, "Test_Book", author, genre, "Test_Description");
    }

    private Author getAuthor(long id) {
        return new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
    }

    private Genre getGenre(long id) {
        return new Genre(id, GENRE_NAME + id);
    }

    private List<Comment> getComments() {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(getComment(ID_1, ID_1));
        comments.add(getComment(ID_2, ID_1));
        comments.add(getComment(ID_3, ID_2));
        comments.add(getComment(ID_4, ID_2));
        return comments;
    }
}