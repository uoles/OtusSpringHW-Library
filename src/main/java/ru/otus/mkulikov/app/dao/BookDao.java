package ru.otus.mkulikov.app.dao;

import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao {

    Book getById(int id);

    List<Book> getAllObjects();

    int addObject(Book book);

    void deleteObject(Book book);

    int updateObject(Book book);
}
