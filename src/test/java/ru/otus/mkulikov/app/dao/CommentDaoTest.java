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
        authorDao.save(getAuthor(1L));
        authorDao.save(getAuthor(2L));
        authorDao.save(getAuthor(3L));

        genreDao.save(getGenre(1L));
        genreDao.save(getGenre(2L));
        genreDao.save(getGenre(3L));

        bookDaoJpa.save(getBook(1L));
        bookDaoJpa.save(getBook(2L));

        commentDaoJpa.save(getComment(1L, 1L));
        commentDaoJpa.save(getComment(2L, 1L));
        commentDaoJpa.save(getComment(3L, 2L));
        commentDaoJpa.save(getComment(4L, 2L));
    }

    @Test
    @DisplayName("Получение комментария по id")
    void getById() {
        Optional<Comment> comment = commentDaoJpa.findById(1L);

        assertThat(comment).isNotEmpty();
        assertThat(comment).contains(getComment(1L, 1L));
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getAllObjects() {
        List<Comment> comments = commentDaoJpa.findAll();

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(4);
        assertThat(comments).containsAll(getComments());
    }

    @Test
    @DisplayName("Получение всех комментариев для книги")
    void getObjectsByBook() {
        Optional<Book> book = bookDaoJpa.findById(1L);
        List<Comment> comments = commentDaoJpa.findByBook(book.orElse(null));

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(2);
        assertThat(comments).contains(getComment(1L, 1L), getComment(2L, 1L));
    }

    @Test
    @DisplayName("Добавление комментария")
    void addObject() {
        Date date = new Date();
        Book book = bookDaoJpa.findById(1L).orElse(null);
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
        commentDaoJpa.deleteById(1L);
        Optional<Comment> comment = commentDaoJpa.findById(1L);
        assertThat(comment).isNotEmpty();
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateObject() {
        Comment comment1 = commentDaoJpa.findById(1L).orElse(null);

        Date date = new Date();
        commentDaoJpa.save(new Comment(1L, comment1.getBook(), date, "TestUser", "TestText"));

        Comment comment2 = commentDaoJpa.findById(1L).orElse(null);

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
        Date date = DateUtil.stringToDateTime("2019-01-01 10:01:01");
        Book book = getBook(bookId);
        return new Comment(id, book, date, "user" + id, "text" + id);
    }

    private Book getBook(long id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        Date date = DateUtil.stringToDateTime("2019-01-01 10:01:01");
        return new Book(id, date, "Test_Book", author, genre, "Test_Description");
    }

    private Author getAuthor(long id) {
        return new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
    }

    private Genre getGenre(long id) {
        return new Genre(id, "Genre" + id);
    }

    private List<Comment> getComments() {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(getComment(1L, 1L));
        comments.add(getComment(2L, 1L));
        comments.add(getComment(3L, 2L));
        comments.add(getComment(4L, 2L));
        return comments;
    }
}