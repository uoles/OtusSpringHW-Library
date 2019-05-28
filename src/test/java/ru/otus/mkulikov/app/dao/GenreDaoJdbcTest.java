package ru.otus.mkulikov.app.dao;

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
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:45
 */

@DisplayName("Класс GenreDaoJdbc")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class GenreDaoJdbcTest {

    private GenreDaoJdbc genreDaoJdbc;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        genreDaoJdbc = new GenreDaoJdbc(new JdbcTemplate(db));
    }

    @Test
    void getById() {
        Genre genre = genreDaoJdbc.getById(1);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(1, genre.getId()),
                  () -> assertEquals("Genre1", genre.getName())
        );
    }

    @Test
    void getAllObjects() {
        List<Genre> genres = genreDaoJdbc.getAllObjects();

        assertAll("genres",
                  () -> assertNotNull(genres),
                  () -> assertEquals(3, genres.size()),
                  () -> assertEquals("Genre1", genres.get(0).getName()),
                  () -> assertEquals("Genre2", genres.get(1).getName()),
                  () -> assertEquals("Genre3", genres.get(2).getName())
        );
    }

    @Test
    void addObject() {
        genreDaoJdbc.addObject(new Genre("Test4"));

        Genre genre = genreDaoJdbc.getById(4);

        assertAll("genre",
                  () -> assertNotNull(genre),
                  () -> assertEquals(4, genre.getId()),
                  () -> assertEquals("Test4", genre.getName())
        );
    }

    @Test
    void deleteObject() {
        int count = genreDaoJdbc.deleteObject(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { genreDaoJdbc.getById(1); })
        );
    }

    @Test
    void updateObject() {
        Genre genre1 = genreDaoJdbc.getById(1);
        int count = genreDaoJdbc.updateObject(new Genre(1, "UpdatedName"));
        Genre genre2 = genreDaoJdbc.getById(1);

        assertAll("genre",
                  () -> assertEquals(1, count),
                  () -> assertEquals("Genre1", genre1.getName()),
                  () -> assertEquals("UpdatedName", genre2.getName())
        );
    }
}