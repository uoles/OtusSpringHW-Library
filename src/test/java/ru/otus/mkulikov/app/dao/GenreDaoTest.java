package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DataMongoTest
@DisplayName("Класс GenreDao")
class GenreDaoTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";
    private final String ID_3 = "3";
    private final String ID_4 = "4";

    private final int OBJECT_COUNT_3 = 3;
    private final int OBJECT_COUNT_2 = 2;

    private final String UPDATED_NAME = "UpdatedName";
    private final String GENRE_NAME = "GenreName";

    @Autowired
    private GenreDao genreDao;

    @BeforeEach
    void init() {
        genreDao.save(getGenre(ID_1));
        genreDao.save(getGenre(ID_2));
        genreDao.save(getGenre(ID_3));
    }

    @Test
    @DisplayName("Получение жанра по id")
    void getById() {
        Optional<Genre> genre = genreDao.findById(ID_1);

        assertThat(genre).isNotEmpty();
        assertThat(genre).contains(getGenre(ID_1));
    }

    @Test
    @DisplayName("Получение всех жанров")
    void getAllObjects() {
        List<Genre> genres = genreDao.findAll();

        assertThat(genres).isNotEmpty();
        assertThat(genres).size().isEqualTo(OBJECT_COUNT_3);
        assertThat(genres).containsAll(getGenres());
    }

    @Test
    @DisplayName("Добавление жанра")
    void addObject() {
        Genre genre = genreDao.save(getGenre(ID_4));
        Optional<Genre> genre_selected = genreDao.findById(genre.getId());

        assertThat(genre_selected).isNotEmpty();
        assertThat(genre_selected).contains(genre);
    }

    @Test
    @DisplayName("Удаление жанра")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
        assertThat(genreDao.count()).isEqualTo(OBJECT_COUNT_3);
        genreDao.deleteById(ID_1);
        assertThat(genreDao.count()).isEqualTo(OBJECT_COUNT_2);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateObject() {
        Genre genre = new Genre(ID_1, UPDATED_NAME);

        genreDao.save(genre);
        Optional<Genre> genre_updated = genreDao.findById(ID_1);

        assertThat(genre_updated).isNotEmpty();
        assertThat(genre_updated).contains(genre);
    }

    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(getGenre(ID_1));
        genres.add(getGenre(ID_2));
        genres.add(getGenre(ID_3));
        return genres;
    }

    private Genre getGenre(String id) {
        return new Genre(id, GENRE_NAME + id);
    }
}