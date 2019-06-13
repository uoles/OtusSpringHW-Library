package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DisplayName("Класс AuthorDaoJdbc")
@RunWith(SpringRunner.class)
@Import(AuthorDaoJpa.class)
@DataJpaTest
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDaoJpa;

    @Test
    void getById() {
        Author author = authorDaoJpa.getById(1L);

        assertAll(
                "author",
                () -> assertNotNull(author),
                () -> assertEquals(1L, author.getId()),
                () -> assertEquals("Surname1", author.getSurname()),
                () -> assertEquals("FirstName1", author.getFirstName()),
                () -> assertEquals("SecondName1", author.getSecondName())
        );
    }

    @Test
    void getAllObjects() {
        List<Author> authors = authorDaoJpa.getAllObjects();

        assertAll(
                "authors",
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
        int count = authorDaoJpa.addObject(author);
        Author author_selected = authorDaoJpa.getById(4L);

        assertAll(
                "author",
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
        assertThrows(DataIntegrityViolationException.class, () -> { authorDaoJpa.deleteObject(1L); });
    }

    @Test
    void updateObject() {
        Author author1 = authorDaoJpa.getById(1L);
        int count = authorDaoJpa.updateObject(
                new Author(1L, "TestSurname", "TestFirstName", "TestSecondName")
        );
        Author author2 = authorDaoJpa.getById(1L);

        assertAll(
                "author",
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