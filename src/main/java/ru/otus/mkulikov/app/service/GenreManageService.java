package ru.otus.mkulikov.app.service;

import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:50
 */
public interface GenreManageService<T extends Genre> {

    T getGenreById(String id);

    List<T> getGenres();

    T addGenre(String name);

    T updateGenre(String id, String name);

    T updateGenre(T t);

    int deleteGenre(String id);
}
