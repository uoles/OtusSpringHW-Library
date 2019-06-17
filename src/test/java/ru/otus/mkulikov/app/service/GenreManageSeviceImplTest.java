package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Genre;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DisplayName("Класс GenreManageSevice")
@RunWith(SpringRunner.class)
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class GenreManageSeviceImplTest {

    @Autowired
    private GenreManageService genreManageService;

    @Test
    void getGenreById() {
        Genre genre = genreManageService.getGenreById(1L);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(1L, genre.getId()),
                () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    void getGenres() {
        List<Genre> genres = genreManageService.getGenres();

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
    void addGenre() {
        int count = genreManageService.addGenre("Test4");
        Genre genre = genreManageService.getGenreById(4L);

        assertAll(
                "genre",
                () -> assertNotNull(genre),
                () -> assertEquals(1, count),
                () -> assertEquals(4L, genre.getId()),
                () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    void updateGenre() {
        Genre genre1 = genreManageService.getGenreById(1L);
        int count = genreManageService.updateGenre(1L, "UpdatedName");
        Genre genre2 = genreManageService.getGenreById(1L);

        assertAll(
                "genre",
                () -> assertEquals(1, count),
                () -> assertEquals("Genre1", genre1.getName()),
                () -> assertEquals("UpdatedName", genre2.getName())
        );
    }

    @Test
    void deleteGenre() {
        assertThrows(PersistenceException.class, () -> { genreManageService.deleteGenre(1L); });
    }
}
