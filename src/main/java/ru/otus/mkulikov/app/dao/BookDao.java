package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao<T extends Book> {

    T getById(long id);

    List<T> getAllObjects();

    int addObject(T t);

    int deleteObject(long id);

    int updateObject(T t);
}
