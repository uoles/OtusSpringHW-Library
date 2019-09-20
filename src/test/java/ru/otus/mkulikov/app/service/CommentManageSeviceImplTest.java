package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.CommentDao;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ru.otus.mkulikov.generators.GenerateBook.getBook;
import static ru.otus.mkulikov.generators.GenerateComment.getComment;
import static ru.otus.mkulikov.generators.GenerateComment.getCommentList;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DisplayName("Класс CommentManageSevice")
@ExtendWith(MockitoExtension.class)
class CommentManageSeviceImplTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";

    private final int OBJECT_COUNT_4 = 4;

    private final String COMMENT_USER_NAME = "user";
    private final String COMMENT_UPDATED_USER_NAME = "TestUser";
    private final String COMMENT_TEXT = "text";
    private final String COMMENT_UPDATED_TEXT = "TestText";

    @Mock
    private CommentDao commentDao;

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private CommentManageServiceImpl commentManageService;

    @Test
    @DisplayName("Получение комментария по id")
    void getCommentById() {
        Comment comment = getComment(ID_1, ID_1);
        when(commentDao.findById(anyString())).thenReturn(Optional.of(comment));
        Comment commentById = commentManageService.getCommentById(ID_1);

        assertThat(commentById).isNotNull();
        assertThat(commentById).isEqualTo(comment);
    }

    @Test
    @DisplayName("Получение всех комментариев")
    void getComments() {
        List<Comment> commentsList = getCommentList();
        when(commentDao.findAll()).thenReturn(commentsList);
        List<Comment> comments = commentManageService.getComments();

        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(OBJECT_COUNT_4);
        assertThat(comments).containsAll(commentsList);
    }

    @Test
    @DisplayName("Добавление комментария")
    void addComment() {
        Book book = getBook(ID_1);

        when(commentDao.save(any(Comment.class))).then(new Answer<Comment>() {
            @Override
            public Comment answer(InvocationOnMock invocationOnMock) throws Throwable {
                Comment comment = (Comment) invocationOnMock.getArgument(0);
                comment.setId("2");
                return comment;
            }
        });
        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));

        Comment comment = commentManageService.addComment(ID_1, COMMENT_USER_NAME, COMMENT_TEXT);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(ID_2);
    }

    @Test
    @DisplayName("Получение комментариев по Id книги")
    void getCommentsByBookId() {
        List<Comment> commentList = getCommentList();
        Optional<Book> book = Optional.of(getBook(ID_1));

        when(bookDao.findById(anyString())).thenReturn(book);
        when(commentDao.findByBook(book.orElse(null))).thenReturn(commentList);

        List<Comment> comments = commentManageService.getCommentsByBookId(ID_1);

        assertThat(comments).isNotNull();
        assertThat(comments).isEqualTo(commentList);
    }

    @Test
    @DisplayName("Обновление комментария")
    void updateComment() {
        when(commentDao.save(any(Comment.class))).then(new Answer<Comment>() {

            @Override
            public Comment answer(InvocationOnMock invocationOnMock) throws Throwable {
                Comment comment = (Comment) invocationOnMock.getArgument(0);
                comment.setId(ID_1);
                return comment;
            }
        });
        when(commentDao.findById(anyString())).thenReturn(Optional.of(getComment(ID_1, ID_1)));

        Comment comment = commentManageService.updateComment(ID_1, COMMENT_UPDATED_USER_NAME, COMMENT_UPDATED_TEXT);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(ID_1);
    }

    @Test
    @DisplayName("Обновление комментария объектом")
    void updateCommentByObject() {
        when(commentDao.save(any(Comment.class))).then(new Answer<Comment>() {

            @Override
            public Comment answer(InvocationOnMock invocationOnMock) throws Throwable {
                Comment comment = (Comment) invocationOnMock.getArgument(0);
                comment.setId(ID_1);
                return comment;
            }
        });
        Comment comment = commentManageService.updateComment(getComment(ID_1, ID_1));

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(ID_1);
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteComment() {
        doThrow(DataIntegrityViolationException.class).when(commentDao).deleteById(ID_1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            commentManageService.deleteComment(ID_1);
        });
    }
}
