package ru.otus.mkulikov.dao;

import ru.otus.mkulikov.domain.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao {

    Book getBookById(Integer id);

    List<Book> getAllBooks();
}
