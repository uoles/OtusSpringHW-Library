package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DisplayName("Класс CommentDaoJpa")
@RunWith(SpringRunner.class)
@Import({CommentDaoJpa.class, BookDaoJpa.class})
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class CommentDaoJpaTest {

    @Autowired
    private BookDao bookDaoJpa;
    @Autowired
    private CommentDao commentDaoJpa;

    @Test
    @DisplayName("Получение комментария по id")
    void getById() {
        Comment comment = commentDaoJpa.getById(1L);

        assertAll(
                "comment",
                () -> assertNotNull(comment),
                () -> assertNotNull(comment.getBook()),
                () -> assertEquals(1L, comment.getId()),
                () -> assertEquals("user1", comment.getUserName()),
                () -> assertEquals("2019-01-01 10:01:01", DateUtil.dateTimeToString(comment.getAddRecordDate())),
                () -> assertEquals("text1", comment.getText())
        );
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getAllObjects() {
        List<Comment> comments = commentDaoJpa.getAllObjects();

        assertAll(
                "comments",
                () -> assertNotNull(comments),
                () -> assertEquals(4, comments.size()),
                () -> assertEquals("text1", comments.get(0).getText()),
                () -> assertEquals("text2", comments.get(1).getText()),
                () -> assertEquals("text3", comments.get(2).getText()),
                () -> assertEquals("text4", comments.get(3).getText())
        );
    }

    @Test
    @DisplayName("Получение всех комментариев для книги")
    void getObjectsByBook() {
        Book book = bookDaoJpa.getById(1L);
        List<Comment> comments = commentDaoJpa.getByBook(book);

        assertAll(
                "comments",
                () -> assertNotNull(comments),
                () -> assertEquals(2, comments.size()),
                () -> assertEquals("text1", comments.get(0).getText()),
                () -> assertEquals("text2", comments.get(1).getText())
        );
    }

    @Test
    @DisplayName("Добавление комментария")
    void addObject() {
        Date date = new Date();
        Book book = bookDaoJpa.getById(1L);
        int count = commentDaoJpa.save(new Comment(book, date, "user5", "text5"));

        Comment comment = commentDaoJpa.getById(5L);

        assertAll(
                "comment",
                () -> assertNotNull(comment),
                () -> assertNotNull(comment.getBook()),
                () -> assertEquals(1, count),
                () -> assertEquals(5L, comment.getId()),
                () -> assertEquals(date, comment.getAddRecordDate()),
                () -> assertEquals("user5", comment.getUserName()),
                () -> assertEquals("text5", comment.getText())
        );
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteObject() {
        commentDaoJpa.deleteObject(1L);
        assertNull(commentDaoJpa.getById(1L));
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateObject() {
        Comment comment1 = commentDaoJpa.getById(1L);

        Date date = new Date();
        int count = commentDaoJpa.save(new Comment(1L, comment1.getBook(), date, "TestUser", "TestText"));

        Comment comment2 = commentDaoJpa.getById(1L);

        assertAll(
                "comment",
                () -> assertNotNull(comment1),
                () -> assertNotNull(comment2),
                () -> assertNotNull(comment1.getBook()),
                () -> assertNotNull(comment2.getBook()),
                () -> assertEquals(1, count),
                () -> assertEquals("user1", comment1.getUserName()),
                () -> assertEquals("text1", comment1.getText()),
                () -> assertEquals(date, comment2.getAddRecordDate()),
                () -> assertEquals("TestUser", comment2.getUserName()),
                () -> assertEquals("TestText", comment2.getText())
        );
    }

}