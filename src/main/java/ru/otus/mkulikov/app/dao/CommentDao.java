package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:48
 */

public interface CommentDao<T extends Comment> {

    T getById(long id);

    List<T> getByBook(Book book);

    int save(T t);

    List<T> getAllObjects();

    int deleteObject(long id);
}
