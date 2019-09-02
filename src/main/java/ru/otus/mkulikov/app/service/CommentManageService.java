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

    T getCommentById(String id);

    List<T> getComments();

    List<T> getCommentsByBookId(String bookId);

    T addComment(String bookId, String userName, String text);

    T updateComment(String id, String userName, String text);

    T updateComment(T t);

    int deleteComment(String id);
}
