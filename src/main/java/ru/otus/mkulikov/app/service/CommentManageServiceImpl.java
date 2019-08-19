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
    public Comment getCommentById(String id) {
        Optional<Comment> comment = commentDao.findById(id);
        return comment.orElse(null);
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        Optional<Book> book = bookDao.findById(bookId);

        return commentDao.findByBook(book.orElse(null));
    }

    @Override
    public Comment addComment(String bookId, String userName, String text) {
        Book book = bookDao.findById(bookId).orElse(null);

        return commentDao.save(new Comment(book, new Date(), userName, text));
    }

    @Override
    public Comment updateComment(String id, String userName, String text) {
        Comment comment = commentDao.findById(id).orElse(null);
        comment.setAddRecordDate(new Date());
        comment.setUserName(userName);
        comment.setText(text);

        return commentDao.save(comment);
    }

    @Override
    public int deleteComment(String id) {
        commentDao.deleteById(id);
        commentDao.count();
        return 1;
    }
}
