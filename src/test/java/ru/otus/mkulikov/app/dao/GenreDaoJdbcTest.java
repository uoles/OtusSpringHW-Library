package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DisplayName("Класс GenreDaoJdbc")
@RunWith(SpringRunner.class)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDaoJdbc;

    @Test
    void getById() {
        Genre genre = genreDaoJdbc.getById(1L);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(1L, genre.getId()),
                () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    void getAllObjects() {
        List<Genre> genres = genreDaoJdbc.getAllObjects();

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
    void addObject() {
        int count = genreDaoJdbc.addObject(new Genre("Test4"));

        Genre genre = genreDaoJdbc.getById(4L);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(1, count),
                () -> assertEquals(4L, genre.getId()),
                () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    void deleteObject() {
        assertThrows(DataIntegrityViolationException.class, () -> { genreDaoJdbc.deleteObject(1L); });
    }

    @Test
    void updateObject() {
        Genre genre1 = genreDaoJdbc.getById(1L);
        int count = genreDaoJdbc.updateObject(new Genre(1L, "UpdatedName"));
        Genre genre2 = genreDaoJdbc.getById(1L);

        assertAll(
                "genre",
                () -> assertEquals(1, count),
                () -> assertEquals("Genre1", genre1.getName()),
                () -> assertEquals("UpdatedName", genre2.getName())
        );
    }
}