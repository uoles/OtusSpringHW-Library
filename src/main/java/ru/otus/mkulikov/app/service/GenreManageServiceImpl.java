package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        Optional<Genre> genre = genreDao.findById(id);
        return genre.get();
    }

    @Override
    public List<Genre> getGenres() {
        Iterable<Genre> authors = genreDao.findAll();
        List<Genre> list = StreamSupport
                .stream(authors.spliterator(), false)
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public int addGenre(String name) {
        genreDao.save(new Genre(name));
        return 1;
    }

    @Override
    public int updateGenre(long id, String name) {
        Genre genre = genreDao.findById(id).get();
        genre.setName(name);

        genreDao.save(genre);
        return 1;
    }

    @Override
    public int deleteGenre(long id) {
        genreDao.deleteById(id);
        genreDao.count();
        return 1;
    }
}
