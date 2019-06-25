package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class AuthorManageSeviceImplTest {

    @Autowired
    private AuthorManageService authorManageService;

    @Test
    @DisplayName("Получение автора по id")
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
    @DisplayName("Получение всех авторов")
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
    @DisplayName("Добавление автора")
    void addAuthor() {
        long id = authorManageService.addAuthor("TestSurname", "TestFirstName", "TestSecondName");
        Author author_selected = authorManageService.getAuthorById(id);

        assertAll(
                "author",
                () -> assertNotNull(author_selected),
                () -> assertNotEquals(0, id),
                () -> assertEquals("TestSurname", author_selected.getSurname()),
                () -> assertEquals("TestFirstName", author_selected.getFirstName()),
                () -> assertEquals("TestSecondName", author_selected.getSecondName())
        );
    }

    @Test
    @DisplayName("Обновление автора")
    void updateAuthor() {
        int count = authorManageService.updateAuthor(1L, "TestSurname", "TestFirstName", "TestSecondName");
        Author author2 = authorManageService.getAuthorById(1L);

        assertAll(
                "author",
                () -> assertEquals(1, count),
                () -> assertEquals("TestSurname", author2.getSurname()),
                () -> assertEquals("TestFirstName", author2.getFirstName()),
                () -> assertEquals("TestSecondName", author2.getSecondName())
        );
    }

    @Test
    @DisplayName("Удаление автора, который используется в таблице книг")
    void deleteAuthor() {
        assertThrows(DataIntegrityViolationException.class, () -> { authorManageService.deleteAuthor(1L); });
    }
}
