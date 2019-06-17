package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Author;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:59
 */

@DisplayName("Класс AuthorManageSevice")
@RunWith(SpringRunner.class)
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class AuthorManageSeviceImplTest {

    @Autowired
    private AuthorManageService authorManageService;

    @Test
    void getAuthorById() {
        Author author = authorManageService.getAuthorById(1L);

        assertAll(
                "author",
                () -> assertNotNull(author),
                () -> assertEquals(1L, author.getId()),
                () -> assertEquals("Surname", author.getSurname()),
                () -> assertEquals("FirstName", author.getFirstName()),
                () -> assertEquals("SecondName", author.getSecondName())
        );
    }

    @Test
    void getAuthors() {
        List<Author> authors = authorManageService.getAuthors();

        assertAll(
                "authors",
                () -> assertNotNull(authors),
                () -> assertEquals(3, authors.size()),
                () -> assertEquals("Surname", authors.get(0).getSurname()),
                () -> assertEquals("Surname2", authors.get(1).getSurname()),
                () -> assertEquals("Surname3", authors.get(2).getSurname())
        );
    }

    @Test
    void addAuthor() {
        int count = authorManageService.addAuthor("TestSurname", "TestFirstName", "TestSecondName");
        Author author_selected = authorManageService.getAuthorById(4L);

        assertAll(
                "author",
                () -> assertNotNull(author_selected),
                () -> assertEquals(1, count),
                () -> assertEquals(4L, author_selected.getId()),
                () -> assertEquals("TestSurname", author_selected.getSurname()),
                () -> assertEquals("TestFirstName", author_selected.getFirstName()),
                () -> assertEquals("TestSecondName", author_selected.getSecondName())
        );
    }

    @Test
    void updateAuthor() {
        Author author1 = authorManageService.getAuthorById(1L);
        int count = authorManageService.updateAuthor(1L, "TestSurname", "TestFirstName", "TestSecondName");
        Author author2 = authorManageService.getAuthorById(1L);

        assertAll(
                "author",
                () -> assertEquals(1, count),
                () -> assertEquals("Surname", author1.getSurname()),
                () -> assertEquals("FirstName", author1.getFirstName()),
                () -> assertEquals("SecondName", author1.getSecondName()),
                () -> assertEquals("TestSurname", author2.getSurname()),
                () -> assertEquals("TestFirstName", author2.getFirstName()),
                () -> assertEquals("TestSecondName", author2.getSecondName())
        );
    }

    @Test
    void deleteAuthor() {
        assertThrows(PersistenceException.class, () -> { authorManageService.deleteAuthor(1L); });
    }
}
