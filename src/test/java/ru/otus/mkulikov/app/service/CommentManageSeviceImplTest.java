package ru.otus.mkulikov.app.service;

import org.junit.Ignore;
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
import static org.mockito.ArgumentMatchers.any;
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
@Ignore
class CommentManageSeviceImplTest {

    @Mock
    private CommentDao commentDao;

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private CommentManageServiceImpl commentManageService;

    @Test
    @DisplayName("Получение комментария по id")
    void getCommentById() {
        Comment comment = getComment(1L);
        when(commentDao.findById(anyLong())).thenReturn( Optional.of(comment) );
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

    @Test
    @DisplayName("Добавление комментария")
    void addComment() {
        Book book = getBook(1L);

        when(commentDao.save(any(Comment.class))).then(new Answer<Comment>() {
            int sequence = 1;

            @Override
            public Comment answer(InvocationOnMock invocationOnMock) throws Throwable {
                Comment comment = (Comment) invocationOnMock.getArgument(0);
                comment.setId(++sequence);
                return comment;
            }
        });
        when(bookDao.findById(anyLong())).thenReturn( Optional.of(book) );

        long id = commentManageService.addComment(1L, "user5", "text5");

        assertThat(id).isEqualTo(2L);
    }

    @Test
    @DisplayName("Получение комментариев по Id книги")
    void getCommentsByBookId() {
        List<Comment> commentList = getCommentList();
        Optional<Book> book = Optional.of(getBook(1L));

        when(bookDao.findById( anyLong() )).thenReturn( book );
        when(commentDao.findByBook( book.orElse(null) )).thenReturn( commentList );

        List<Comment> comments = commentManageService.getCommentsByBookId(1L);

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
                comment.setId(1L);
                return comment;
            }
        });
        when(commentDao.findById(anyLong())).thenReturn( Optional.of(getComment(1L)) );

        int count = commentManageService.updateComment(1L, "TestUser", "TestText");

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление комментария")
    void deleteComment() {
        doThrow(DataIntegrityViolationException.class).when(commentDao).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> { commentManageService.deleteComment(1L); });
    }

    private Comment getComment(long id) {
        Date date = DateUtil.stringToDateTime("2019-01-01 10:01:01");
        Book book = getBook(id);
        return new Comment(id, book, date, "user" + id, "text" + id);
    }

    private Book getBook(long id) {
        Author author = new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
        Genre genre = new Genre(id, "Genre" + id);
        return new Book(id, "Test_Book", author, genre, "Test_Description");
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
