package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DataJpaTest
@DisplayName("Класс GenreDaoJpa")
@ComponentScan("ru.otus.mkulikov.app")
@TestPropertySource(locations= "classpath:application.yml")
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("Получение жанра по id")
    void getById() {
        Genre genre = genreDao.findById(1L).orElse(null);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(1L, genre.getId()),
                () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    @DisplayName("Получение всех жанров")
    void getAllObjects() {
        List<Genre> genres = genreDao.findAll();

        assertAll(
                "genres",
                () -> assertNotNull(genres),
                () -> assertEquals(3, genres.size()),
                () -> assertEquals("Genre1", genres.get(0).getName()),
                () -> assertEquals("Genre2", genres.get(1).getName()),
                () -> assertEquals("Genre3", genres.get(2).getName())
        );
    }

    @Test
    @DisplayName("Добавление жанра")
    void addObject() {
        genreDao.save(new Genre("Test4"));
        Genre genre = genreDao.findById(4L).orElse(null);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(4L, genre.getId()),
                () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    @DisplayName("Удаление жанра, который используется в таблице книг")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
        assertThrows(DataIntegrityViolationException.class, () -> genreDao.deleteById(1L));
        assertThat(genreDao.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateObject() {
        genreDao.save(new Genre(1L, "UpdatedName"));
        Genre genre = genreDao.findById(1L).orElse(null);

        assertAll(
                "genre",
                () -> assertEquals(1L, genre.getId()),
                () -> assertEquals("UpdatedName", genre.getName())
        );
    }
}