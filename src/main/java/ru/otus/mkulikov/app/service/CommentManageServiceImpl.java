package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.CommentDao;
import ru.otus.mkulikov.app.exception.ObjectNotFound;
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
    public Comment getCommentById(String id) {
        return commentDao.findById(id).orElseThrow(() -> new ObjectNotFound("Comment", id));
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new ObjectNotFound("Book", bookId));
        return commentDao.findByBook(book);
    }

    @Override
    public Comment addComment(String bookId, String userName, String text) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new ObjectNotFound("Book", bookId));
        return commentDao.save(new Comment(book, new Date(), userName, text));
    }

    @Override
    public Comment updateComment(String id, String userName, String text) {
        Comment comment = commentDao.findById(id).orElseThrow(() -> new ObjectNotFound("Comment", id));
        comment.setAddRecordDate(new Date());
        comment.setUserName(userName);
        comment.setText(text);

        return commentDao.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Comment orig = commentDao.findById(comment.getId()).orElseThrow(() -> new ObjectNotFound("Comment", comment.getId()));
        orig.setAddRecordDate(new Date());
        orig.setUserName(comment.getUserName());
        orig.setText(comment.getText());

        return commentDao.save(orig);
    }

    @Override
    public int deleteComment(String id) {
        commentDao.deleteById(id);
        return 1;
    }
}
