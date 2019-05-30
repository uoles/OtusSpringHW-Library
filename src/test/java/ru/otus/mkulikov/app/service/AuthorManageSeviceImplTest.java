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
import ru.otus.mkulikov.app.dao.AuthorDaoJdbc;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:59
 */

@DisplayName("Класс AuthorManageSevice")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class AuthorManageSeviceImplTest {

    private AuthorManageService authorManageService;

    @BeforeEach
    public void setup() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("test_schema.sql")
                .addScript("test_data.sql")
                .build();

        AuthorDaoJdbc authorDaoJdbc = new AuthorDaoJdbc(new JdbcTemplate(db));
        authorManageService = new AuthorManageServiceImpl(authorDaoJdbc);
    }

    @Test
    void getAuthorById() {
        Author author = authorManageService.getAuthorById(1);

        assertAll("author",
                  () -> assertNotNull(author),
                  () -> assertEquals(1, author.getId()),
                  () -> assertEquals("Surname1", author.getSurname()),
                  () -> assertEquals("FirstName1", author.getFirstName()),
                  () -> assertEquals("SecondName1", author.getSecondName())
        );
    }

    @Test
    void getAuthors() {
        List<Author> authors = authorManageService.getAuthors();

        assertAll("authors",
                  () -> assertNotNull(authors),
                  () -> assertEquals(3, authors.size()),
                  () -> assertEquals("Surname1", authors.get(0).getSurname()),
                  () -> assertEquals("Surname2", authors.get(1).getSurname()),
                  () -> assertEquals("Surname3", authors.get(2).getSurname())
        );
    }

    @Test
    void addAuthor() {
        int count = authorManageService.addAuthor("TestSurname", "TestFirstName", "TestSecondName");
        Author author_selected = authorManageService.getAuthorById(4);

        assertAll("author",
                  () -> assertNotNull(author_selected),
                  () -> assertEquals(1, count),
                  () -> assertEquals(4, author_selected.getId()),
                  () -> assertEquals("TestSurname", author_selected.getSurname()),
                  () -> assertEquals("TestFirstName", author_selected.getFirstName()),
                  () -> assertEquals("TestSecondName", author_selected.getSecondName())
        );
    }

    @Test
    void updateAuthor() {
        Author author1 = authorManageService.getAuthorById(1);
        int count = authorManageService.updateAuthor(1,"TestSurname", "TestFirstName", "TestSecondName");
        Author author2 = authorManageService.getAuthorById(1);

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

    @Test
    void deleteAuthor() {
        int count = authorManageService.deleteAuthor(1);

        assertAll("author",
                  () -> assertEquals(1, count),
                  () -> assertThrows(EmptyResultDataAccessException.class, () -> { authorManageService.getAuthorById(1); })
        );
    }
}
