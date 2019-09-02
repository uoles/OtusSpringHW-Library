package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.exception.ObjectNotFound;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:52
 */

@Service
@RequiredArgsConstructor
public class GenreManageServiceImpl implements GenreManageService {

    private final GenreDao genreDao;

    @Override
    public Genre getGenreById(String id) {
        return genreDao.findById(id).orElseThrow(() -> new ObjectNotFound("Genre", id));
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.findAll();
    }

    @Override
    public Genre addGenre(String name) {
        return genreDao.save(new Genre(name));
    }

    @Override
    public Genre updateGenre(String id, String name) {
        Genre genre = genreDao.findById(id).orElseThrow(() -> new ObjectNotFound("Genre", id));
        genre.setName(name);

        return genreDao.save(genre);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        Genre orig = genreDao.findById(genre.getId()).orElseThrow(() -> new ObjectNotFound("Genre", genre.getId()));
        orig.setName(genre.getName());

        return genreDao.save(orig);
    }

    @Override
    public int deleteGenre(String id) {
        genreDao.deleteById(id);
        return 1;
    }
}
