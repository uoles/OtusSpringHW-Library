package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:48
 */

public interface CommentDao<T extends Comment> {

    T getById(long id);

    T getByBookId(long id);

    List<T> getAllObjects();

    int addObject(T t);

    int deleteObject(long id);

    int updateObject(T t);
}
