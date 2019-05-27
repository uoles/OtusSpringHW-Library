package ru.otus.mkulikov.app.dao;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao<T> {

    T getById(int id);

    List<T> getAllObjects();

    int addObject(T t);

    void deleteObject(T t);

    int updateObject(T t);
}
