package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

public interface AuthorDao {

    Author getById(int id);

    List<Author> getAllObjects();

    int addObject(Book book);

    void deleteObject(Book book);

    int updateObject(Book book);
}
