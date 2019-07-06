package ru.otus.mkulikov.app.service;

import ru.otus.mkulikov.app.model.Comment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:50
 */
public interface CommentManageService<T extends Comment> {

    T getCommentById(long id);

    List<T> getComments();

    List<T> getCommentsByBookId(long bookId);

    long addComment(long bookId, String userName, String text);

    int updateComment(long id, String userName, String text);

    int deleteComment(long id);
}
