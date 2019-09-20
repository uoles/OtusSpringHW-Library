package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateBook.getBook;
import static ru.otus.mkulikov.generators.GenerateComment.getComment;
import static ru.otus.mkulikov.generators.GenerateComment.getCommentList;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenre;

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

    private final String COMMENT_USER_NAME = "user";
    private final String COMMENT_UPDATED_USER_NAME = "TestUser";
    private final String COMMENT_TEXT = "text";
    private final String COMMENT_UPDATED_TEXT = "TestText";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CommentDao commentDao;

    @BeforeEach
    void init() {
        mongoTemplate.save(getAuthor(ID_1));
        mongoTemplate.save(getAuthor(ID_2));
        mongoTemplate.save(getAuthor(ID_3));

        mongoTemplate.save(getGenre(ID_1));
        mongoTemplate.save(getGenre(ID_2));
        mongoTemplate.save(getGenre(ID_3));

        mongoTemplate.save(getBook(ID_1));
        mongoTemplate.save(getBook(ID_2));

        mongoTemplate.save(getComment(ID_1, ID_1));
        mongoTemplate.save(getComment(ID_2, ID_1));
        mongoTemplate.save(getComment(ID_3, ID_2));
        mongoTemplate.save(getComment(ID_4, ID_2));
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
        assertThat(comments).containsAll(getCommentList());
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

    private Comment getUpdatedComment() {
        Comment comment = commentDao.findById(ID_1).orElse(null);
        comment.setAddRecordDate(new Date());
        comment.setUserName(COMMENT_UPDATED_USER_NAME);
        comment.setText(COMMENT_UPDATED_TEXT);
        return comment;
    }
}