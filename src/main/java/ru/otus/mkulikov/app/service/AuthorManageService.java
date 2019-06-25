package ru.otus.mkulikov.app.service;

import ru.otus.mkulikov.app.model.Author;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 14:59
 */

public interface AuthorManageService<T extends Author> {

    T getAuthorById(long id);

    List<T> getAuthors();

    long addAuthor(String surname, String firstName, String secondName);

    int updateAuthor(long id, String surname, String firstName, String secondName);

    int deleteAuthor(long id);
}
