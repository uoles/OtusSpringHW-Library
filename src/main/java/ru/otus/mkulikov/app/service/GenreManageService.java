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

    T getGenreById(long id);

    List<T> getGenres();

    long addGenre(String name);

    int updateGenre(long id, String name);

    int deleteGenre(long id);
}
