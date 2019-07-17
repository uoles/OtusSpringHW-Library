package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.CommentDao;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        Optional<Comment> comment = commentDao.findById(id);
        return comment.orElse(null);
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentDao.findByBookId(bookId);
    }

    @Override
    public long addComment(long bookId, String userName, String text) {
        Book book = bookDao.findById(bookId).orElse(null);

        Comment comment = commentDao.save(new Comment(book, new Date(), userName, text));
        return comment.getId();
    }

    @Override
    public int updateComment(long id, String userName, String text) {
        Comment comment = commentDao.findById(id).orElse(null);
        comment.setAddRecordDate(new Date());
        comment.setUserName(userName);
        comment.setText(text);

        commentDao.save(comment);
        return 1;
    }

    @Override
    public int deleteComment(long id) {
        commentDao.deleteById(id);
        commentDao.count();
        return 1;
    }
}
