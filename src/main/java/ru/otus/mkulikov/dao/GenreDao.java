package ru.otus.mkulikov.dao;

import ru.otus.mkulikov.model.Book;
import ru.otus.mkulikov.model.Genre;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

public interface GenreDao {

    Genre getById(int id);

    int addObject(Book book);

    void deleteObject(Book book);

    int updateObject(Book book);

}
