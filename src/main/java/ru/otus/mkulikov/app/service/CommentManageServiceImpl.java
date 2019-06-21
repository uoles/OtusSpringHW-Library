package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.CommentDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;

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

    @Override
    public Comment getCommentById(long id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.getAllObjects();
    }

    @Override
    public int addComment(long bookId, String userName, String text) {
        return commentDao.addObject(new Comment(bookId, new Date(), userName, text));
    }

    @Override
    public int updateComment(long id, long bookId, String userName, String text) {
        return commentDao.updateObject(new Comment(id, bookId, new Date(), userName, text));
    }

    @Override
    public int deleteComment(long id) {
        return commentDao.deleteObject(id);
    }
}
