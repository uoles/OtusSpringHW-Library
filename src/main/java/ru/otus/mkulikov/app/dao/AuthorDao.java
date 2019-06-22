package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Author;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

public interface AuthorDao<T extends Author> {

    T getById(long id);

    List<T> getAllObjects();

    int save(T t);

    int deleteObject(long id);
}
