package ru.otus.mkulikov.dao;

import ru.otus.mkulikov.model.Book;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

public interface AuthorDao {

    int addObject(Book book);

    void deleteObject(Book book);

    int updateObject(Book book);

}
