package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.GenreDao;
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
    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.getAllObjects();
    }

    @Override
    public int addGenre(String name) {
        return genreDao.save(new Genre(name));
    }

    @Override
    public int updateGenre(long id, String name) {
        return genreDao.save(new Genre(id, name));
    }

    @Override
    public int deleteGenre(long id) {
        return genreDao.deleteObject(id);
    }
}
