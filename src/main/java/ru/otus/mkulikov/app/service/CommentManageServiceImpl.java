package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.CommentDao;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:52
 */

@Service
@RequiredArgsConstructor
public class CommentManageServiceImpl implements CommentManageService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Override
    public Comment getCommentById(long id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.getAllObjects();
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        Book book = bookDao.getById(bookId);

        return commentDao.getByBook(book);
    }

    @Override
    public int addComment(long bookId, String userName, String text) {
        Book book = bookDao.getById(bookId);

        return commentDao.save(new Comment(book, new Date(), userName, text));
    }

    @Override
    public int updateComment(long id, String userName, String text) {
        Comment comment = commentDao.getById(id);
        comment.setAddRecordDate(new Date());
        comment.setUserName(userName);
        comment.setText(text);

        return commentDao.save(comment);
    }

    @Override
    public int deleteComment(long id) {
        return commentDao.deleteObject(id);
    }
}
