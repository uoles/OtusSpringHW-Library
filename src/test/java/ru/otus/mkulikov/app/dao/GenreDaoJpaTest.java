package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DataMongoTest
@DisplayName("Класс GenreDaoJpa")
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDao;

    @BeforeEach
    void init() {
        genreDao.save(getGenre(1L));
        genreDao.save(getGenre(2L));
        genreDao.save(getGenre(3L));
    }

    @Test
    @DisplayName("Получение жанра по id")
    void getById() {
        Optional<Genre> genre = genreDao.findById(1L);

        assertThat(genre).isNotEmpty();
        assertThat(genre).contains(getGenre(1L));
    }

    @Test
    @DisplayName("Получение всех жанров")
    void getAllObjects() {
        List<Genre> genres = genreDao.findAll();

        assertThat(genres).isNotEmpty();
        assertThat(genres).size().isEqualTo(3);
        assertThat(genres).containsAll(getGenres());
    }

    @Test
    @DisplayName("Добавление жанра")
    void addObject() {
        Genre genre = genreDao.save(getGenre(4L));
        Optional<Genre> genre_selected = genreDao.findById(genre.getId());

        assertThat(genre_selected).isNotEmpty();
        assertThat(genre_selected).contains(genre);
    }

    @Test
    @DisplayName("Удаление жанра")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
//        assertThrows(DataIntegrityViolationException.class, () -> genreDao.deleteById(1L));

        assertThat(genreDao.count()).isEqualTo(3);
        genreDao.deleteById(1L);
        assertThat(genreDao.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateObject() {
        Genre genre = new Genre(1L, "UpdatedName");

        genreDao.save(genre);
        Optional<Genre> genre_updated = genreDao.findById(1L);

        assertThat(genre_updated).isNotEmpty();
        assertThat(genre_updated).contains(genre);
    }

    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(getGenre(1L));
        genres.add(getGenre(2L));
        genres.add(getGenre(3L));
        return genres;
    }

    private Genre getGenre(long id) {
        return new Genre(id, "Genre" + id);
    }
}