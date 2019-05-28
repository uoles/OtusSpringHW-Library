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

    T getById(int id);

    List<T> getAllObjects();

    int addObject(T t);

    int deleteObject(int id);

    int updateObject(T t);
}
