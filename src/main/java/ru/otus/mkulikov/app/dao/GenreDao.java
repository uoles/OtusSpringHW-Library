package ru.otus.mkulikov.app.dao;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

public interface GenreDao<T> {

    T getById(int id);

    List<T> getAllObjects();

    int addObject(T t);

    int deleteObject(int id);

    int updateObject(T t);
}
