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

    T getBookById(String id);

    List<T> getBooks();

    T addBook(String caption, String authorId, String genreId, String description);

    T updateBook(String id, String caption, String authorId, String genreId, String description);

    T updateBook(T t);

    int deleteBook(String id);
}
