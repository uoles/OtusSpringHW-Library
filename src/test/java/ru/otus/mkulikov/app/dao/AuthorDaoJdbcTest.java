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
import ru.otus.mkulikov.app.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DisplayName("Класс AuthorDaoJdbc")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class AuthorDaoJdbcTest {

    private AuthorDaoJdbc authorDaoJdbc;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        authorDaoJdbc = new AuthorDaoJdbc(new JdbcTemplate(db));
    }

    @Test
    void getById() {
        Author author = authorDaoJdbc.getById(1);

        assertAll("author",
                  () -> assertNotNull(author),
                  () -> assertEquals(1, author.getId()),
                  () -> assertEquals("Surname1", author.getSurname()),
                  () -> assertEquals("FirstName1", author.getFirstName()),
                  () -> assertEquals("SecondName1", author.getSecondName())
        );
    }

    @Test
    void getAllObjects() {
        List<Author> authors = authorDaoJdbc.getAllObjects();

        assertAll("authors",
                  () -> assertNotNull(authors),
                  () -> assertEquals(3, authors.size()),
                  () -> assertEquals("Surname1", authors.get(0).getSurname()),
                  () -> assertEquals("Surname2", authors.get(1).getSurname()),
                  () -> assertEquals("Surname3", authors.get(2).getSurname())
        );
    }

    @Test
    void addObject() {
        Author author = new Author("TestSurname", "TestFirstName", "TestSecondName");
        int count = authorDaoJdbc.addObject(author);
        Author author_selected = authorDaoJdbc.getById(4);

        assertAll("author",
                  () -> assertNotNull(author_selected),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, author_selected.getId()),
                  () -> assertEquals(author.getSurname(), author_selected.getSurname()),
                  () -> assertEquals(author.getFirstName(), author_selected.getFirstName()),
                  () -> assertEquals(author.getSecondName(), author_selected.getSecondName())
        );
    }

    @Test
    void deleteObject() {
        int count = authorDaoJdbc.deleteObject(1);

        assertAll("author",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { authorDaoJdbc.getById(1); })
        );
    }

    @Test
    void updateObject() {
        Author author1 = authorDaoJdbc.getById(1);
        int count = authorDaoJdbc.updateObject(
                new Author(1,"TestSurname", "TestFirstName", "TestSecondName")
        );
        Author author2 = authorDaoJdbc.getById(1);

        assertAll("author",
                  () -> assertEquals(1, count),
                  () -> assertEquals("Surname1", author1.getSurname()),
                  () -> assertEquals("FirstName1", author1.getFirstName()),
                  () -> assertEquals("SecondName1", author1.getSecondName()),
                  () -> assertEquals("TestSurname", author2.getSurname()),
                  () -> assertEquals("TestFirstName", author2.getFirstName()),
                  () -> assertEquals("TestSecondName", author2.getSecondName())
        );
    }
}