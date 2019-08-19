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

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DataMongoTest
@DisplayName("Класс CommentDao")
class CommentDaoTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";
    private final String ID_3 = "3";
    private final String ID_4 = "4";
    private final String ID_5 = "5";

    private final int OBJECT_COUNT_4 = 4;
    private final int OBJECT_COUNT_2 = 2;

    private final String DATE_TIME = "2019-01-01 10:01:01";
    private final String GENRE_NAME = "GenreName";

    private final String COMMENT_USER_NAME = "user";
    private final String COMMENT_UPDATED_USER_NAME = "TestUser";
    private final String COMMENT_TEXT = "text";
    private final String COMMENT_UPDATED_TEXT = "TestText";

    private final String AUTHOR_SURNAME = "Surname";
    private final String AUTHOR_FIRST_NAME = "FirstName";
    private final String AUTHOR_SECOND_NAME = "SecondName";

    private final String BOOK_NAME = "BookName";
    private final String BOOK_DESCRIPTION = "Description";

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CommentDao commentDao;

    @BeforeEach
    void init() {
        authorDao.save(getAuthor(ID_1));
        authorDao.save(getAuthor(ID_2));
        authorDao.save(getAuthor(ID_3));

        genreDao.save(getGenre(ID_1));
        genreDao.save(getGenre(ID_2));
        genreDao.save(getGenre(ID_3));

        bookDao.save(getBook(ID_1));
        bookDao.save(getBook(ID_2));

        commentDao.save(getComment(ID_1, ID_1));
        commentDao.save(getComment(ID_2, ID_1));
        commentDao.save(getComment(ID_3, ID_2));
        commentDao.save(getComment(ID_4, ID_2));
    }

    @Test
    @DisplayName("Получение комментария по id")
    void getById() {
        Optional<Comment> comment = commentDao.findById(ID_1);

        assertThat(comment).isNotEmpty();
        assertThat(comment).contains(getComment(ID_1, ID_1));
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getAllObjects() {
        List<Comment> comments = commentDao.findAll();

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(OBJECT_COUNT_4);
        assertThat(comments).containsAll(getComments());
    }

    @Test
    @DisplayName("Получение всех комментариев для книги")
    void getObjectsByBook() {
        Optional<Book> book = bookDao.findById(ID_1);
        List<Comment> comments = commentDao.findByBook(book.orElse(null));

        assertThat(comments).isNotEmpty();
        assertThat(comments).size().isEqualTo(OBJECT_COUNT_2);
        assertThat(comments).contains(getComment(ID_1, ID_1), getComment(ID_2, ID_1));
    }

    @Test
    @DisplayName("Добавление комментария")
    void addObject() {
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        Book book = bookDao.findById(ID_1).orElse(null);
        Comment comment = new Comment(ID_5, book, date, COMMENT_USER_NAME + ID_5, COMMENT_TEXT + ID_5);
        commentDao.save(comment);

        Optional<Comment> comment_selected = commentDao.findById(ID_5);

        assertThat(comment_selected).isNotEmpty();
        assertThat(comment_selected).contains(comment);
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteObject() {
        commentDao.deleteById(ID_1);
        Optional<Comment> comment = commentDao.findById(ID_1);
        assertThat(comment).isEmpty();
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateObject() {
        Comment comment = getUpdatedComment();
        commentDao.save(comment);
        Optional<Comment> comment_selected = commentDao.findById(ID_1);

        assertThat(comment_selected).isNotEmpty();
        assertThat(comment_selected).contains(comment);
    }

    private Comment getComment(String id, String bookId) {
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        Book book = getBook(bookId);
        return new Comment(id, book, date, COMMENT_USER_NAME + id, COMMENT_TEXT + id);
    }

    private Book getBook(String id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        return new Book(id, date, BOOK_NAME, author, genre, BOOK_DESCRIPTION);
    }

    private Author getAuthor(String id) {
        return new Author(id, AUTHOR_SURNAME + id, AUTHOR_FIRST_NAME + id, AUTHOR_SECOND_NAME + id);
    }

    private Genre getGenre(String id) {
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

    private Comment getUpdatedComment() {
        Comment comment = commentDao.findById(ID_1).orElse(null);
        comment.setAddRecordDate(new Date());
        comment.setUserName(COMMENT_UPDATED_USER_NAME);
        comment.setText(COMMENT_UPDATED_TEXT);
        return comment;
    }
}