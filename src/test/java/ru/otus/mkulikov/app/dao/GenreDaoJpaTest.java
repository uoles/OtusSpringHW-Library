package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Genre;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DisplayName("Класс GenreDaoJpa")
@RunWith(SpringRunner.class)
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDaoJpa;

    @Test
    @DisplayName("Получение жанра по id")
    void getById() {
        Genre genre = genreDaoJpa.findById(1L).get();

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
        Iterable<Genre> iterable = genreDaoJpa.findAll();
        List<Genre> genres = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

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
        genreDaoJpa.save(new Genre("Test4"));
        Genre genre = genreDaoJpa.findById(4L).get();

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(4L, genre.getId()),
                () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    @DisplayName("Удаление жанра, который используется в таблице книг")
    void deleteObject() {
        genreDaoJpa.deleteById(1L);
        Genre genre = genreDaoJpa.findById(1L).get();

        assertNotNull(genre);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateObject() {
        genreDaoJpa.save(new Genre(1L, "UpdatedName"));
        Genre genre = genreDaoJpa.findById(1L).get();

        assertAll(
                "genre",
                () -> assertEquals(1L, genre.getId()),
                () -> assertEquals("UpdatedName", genre.getName())
        );
    }
}