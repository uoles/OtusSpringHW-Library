package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DataJpaTest
@DisplayName("Класс CommentManageSevice")
@ComponentScan("ru.otus.mkulikov.app")
@TestPropertySource(locations= "classpath:application.yml")
class CommentManageSeviceImplTest {

    @Autowired
    private CommentManageService commentManageService;

    @Test
    @DisplayName("Получение комментария по id")
    void getCommentById() {
        Comment comment = commentManageService.getCommentById(1L);

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
    void getComments() {
        List<Comment> comments = commentManageService.getComments();

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
    @DisplayName("Добавление комментария")
    void addComment() {
        long id = commentManageService.addComment(1L, "user5", "text5");
        Comment comment = commentManageService.getCommentById(id);

        assertAll(
                "comment",
                () -> assertNotNull(comment),
                () -> assertNotNull(comment.getBook()),
                () -> assertNotEquals(0, id),
                () -> assertEquals("user5", comment.getUserName()),
                () -> assertEquals("text5", comment.getText())
        );
    }

    @Test
    @DisplayName("Получение комментариев по Id книги")
    void getCommentsByBookId() {
        List<Comment> comments = commentManageService.getCommentsByBookId(1L);

        assertAll(
                "comments",
                () -> assertNotNull(comments),
                () -> assertEquals(2, comments.size()),
                () -> assertEquals("text1", comments.get(0).getText()),
                () -> assertEquals("text2", comments.get(1).getText())
        );
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateComment() {
        Comment comment1 = commentManageService.getCommentById(1L);
        int count = commentManageService.updateComment(1L, "TestUser", "TestText");
        Comment comment2 = commentManageService.getCommentById(1L);

        assertAll(
                "comment",
                () -> assertNotNull(comment1),
                () -> assertNotNull(comment2),
                () -> assertNotNull(comment1.getBook()),
                () -> assertNotNull(comment2.getBook()),
                () -> assertEquals(1, count),
                () -> assertEquals("user1", comment1.getUserName()),
                () -> assertEquals("text1", comment1.getText()),
                () -> assertEquals("TestUser", comment2.getUserName()),
                () -> assertEquals("TestText", comment2.getText())
        );
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteComment() {
        commentManageService.deleteComment(1L);
        assertNull(commentManageService.getCommentById(1L));
    }
}
