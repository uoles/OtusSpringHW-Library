package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

public interface GenreDao {

    Genre getById(int id);

    List<Genre> getAllObjects();

    int addObject(Book book);

    void deleteObject(Book book);

    int updateObject(Book book);
}
