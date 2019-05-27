package ru.otus.mkulikov.app.services;

import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 27.05.2019
 * Time: 13:42
 */

public interface BooksManageSevice {

    Book getBookById(int id);

    List<Book> getBooks();

    Author getAuthorById(int id);

    List<Author>  getAuthors();

    Genre getGenreById(int id);

    List<Genre>  getGenres();

    int addGenre(String name);

    int updateGenre(int id, String name);

    int deleteGenre(int id);
}
