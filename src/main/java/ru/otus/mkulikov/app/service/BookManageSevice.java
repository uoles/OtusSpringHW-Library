package ru.otus.mkulikov.app.service;

import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 27.05.2019
 * Time: 13:42
 */

public interface BookManageSevice<T extends Book> {

    T getBookById(long id);

    List<T> getBooks();

    int addBook(String caption, long authorId, long genreId, String description);

    int updateBook(long id, String caption, long authorId, long genreId, String description);

    int deleteBook(long id);
}
