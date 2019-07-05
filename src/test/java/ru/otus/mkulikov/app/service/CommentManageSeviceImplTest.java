package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import ru.otus.mkulikov.app.dao.CommentDao;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DisplayName("Класс CommentManageSevice")
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations= "classpath:application.yml")
class CommentManageSeviceImplTest {

    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private CommentManageServiceImpl commentManageService;

    @Test
    @DisplayName("Получение комментария по id")
    void getCommentById() {
        Comment comment = getComment(1L);
        when(commentDao.getById(anyLong())).thenReturn( Optional.of(comment) );
        Comment commentById = commentManageService.getCommentById(1L);

        assertThat(commentById).isNotNull();
        assertThat(commentById).isEqualTo(comment);
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getComments() {
        List<Comment> commentsList = getCommentList();
        when(commentDao.findAll()).thenReturn( commentsList );
        List<Comment> comments = commentManageService.getComments();

        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(4);
        assertThat(comments).containsAll(commentsList);
    }

    //@Test
    //@DisplayName("Добавление комментария")
    //void addComment() {
    //    long id = commentManageService.addComment(1L, "user5", "text5");
    //    Comment comment = commentManageService.getCommentById(id);
    //
    //    assertAll(
    //            "comment",
    //            () -> assertNotNull(comment),
    //            () -> assertNotNull(comment.getBook()),
    //            () -> assertNotEquals(0, id),
    //            () -> assertEquals("user5", comment.getUserName()),
    //            () -> assertEquals("text5", comment.getText())
    //    );
    //}

    //@Test
    //@DisplayName("Получение комментариев по Id книги")
    //void getCommentsByBookId() {
    //    List<Comment> comments = commentManageService.getCommentsByBookId(1L);
    //
    //    assertAll(
    //            "comments",
    //            () -> assertNotNull(comments),
    //            () -> assertEquals(2, comments.size()),
    //            () -> assertEquals("text1", comments.get(0).getText()),
    //            () -> assertEquals("text2", comments.get(1).getText())
    //    );
    //}

    //@Test
    //@DisplayName("Обновление комментария")
    //void updateComment() {
    //    Comment comment1 = commentManageService.getCommentById(1L);
    //    int count = commentManageService.updateComment(1L, "TestUser", "TestText");
    //    Comment comment2 = commentManageService.getCommentById(1L);
    //
    //    assertAll(
    //            "comment",
    //            () -> assertNotNull(comment1),
    //            () -> assertNotNull(comment2),
    //            () -> assertNotNull(comment1.getBook()),
    //            () -> assertNotNull(comment2.getBook()),
    //            () -> assertEquals(1, count),
    //            () -> assertEquals("user1", comment1.getUserName()),
    //            () -> assertEquals("text1", comment1.getText()),
    //            () -> assertEquals("TestUser", comment2.getUserName()),
    //            () -> assertEquals("TestText", comment2.getText())
    //    );
    //}

    @Test
    @DisplayName("Удаление комментария")
    void deleteComment() {
        doThrow(DataIntegrityViolationException.class).when(commentDao).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> { commentManageService.deleteComment(1L); });
    }

    private Comment getComment(long id) {
        Date date = DateUtil.stringToDateTime("2019-01-01 10:01:01");
        Author author = new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
        Genre genre = new Genre(id, "Genre" + id);
        Book book = new Book(id, "Test_Book", author, genre, "Test_Description");
        return new Comment(id, book, date, "user" + id, "text" + id);
    }

    private List<Comment> getCommentList() {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(getComment(1L));
        comments.add(getComment(2L));
        comments.add(getComment(3L));
        comments.add(getComment(4L));
        return comments;
    }
}
