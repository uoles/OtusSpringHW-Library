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

    T getFullBookById(long id);

    List<T> getBooks();

    List<T> getFullBooks();

    int addBook(String caption, int authorId, int genreId, String comment);

    int updateBook(long id, String caption, int authorId, int genreId, String comment);

    int deleteBook(long id);
}
