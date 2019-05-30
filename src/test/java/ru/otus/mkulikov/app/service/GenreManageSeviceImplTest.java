package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.AppTestConfig;
import ru.otus.mkulikov.app.dao.GenreDaoJdbc;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DisplayName("Класс GenreManageSevice")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class GenreManageSeviceImplTest {

    private GenreManageService genreManageService;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        GenreDaoJdbc genreDaoJdbc = new GenreDaoJdbc(new JdbcTemplate(db));
        genreManageService = new GenreManageServiceImpl(genreDaoJdbc);
    }

    @Test
    void getGenreById() {
        Genre genre = genreManageService.getGenreById(1);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(1, genre.getId()),
                  () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    void getGenres() {
        List<Genre> genres = genreManageService.getGenres();

        assertAll("genres",
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
        Genre genre = genreManageService.getGenreById(4);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, genre.getId()),
                  () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    void updateGenre() {
        Genre genre1 = genreManageService.getGenreById(1);
        int count = genreManageService.updateGenre(1, "UpdatedName");
        Genre genre2 = genreManageService.getGenreById(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertEquals("Genre1", genre1.getName()),
                  () -> assertEquals("UpdatedName", genre2.getName())
        );
    }

    @Test
    void deleteGenre() {
        int count = genreManageService.deleteGenre(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { genreManageService.getGenreById(1); })
        );
    }
}
