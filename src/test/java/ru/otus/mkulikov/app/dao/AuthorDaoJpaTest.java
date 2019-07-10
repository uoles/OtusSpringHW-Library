package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DataJpaTest
@DisplayName("Класс AuthorDaoJpa")
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Получение автора по id")
    void getById() {
        Optional<Author> author = authorDao.findById(1L);

        assertAll(
                "author",
                () -> assertNotNull(author.orElse(null)),
                () -> assertEquals(1L, author.orElse(null).getId()),
                () -> assertEquals("Surname", author.orElse(null).getSurname()),
                () -> assertEquals("FirstName", author.orElse(null).getFirstName()),
                () -> assertEquals("SecondName", author.orElse(null).getSecondName())
        );
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAllObjects() {
        List<Author> authors = authorDao.findAll();

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
    void addObject() {
        Author author = authorDao.save(new Author("TestSurname", "TestFirstName", "TestSecondName"));
        Optional<Author> author_selected = authorDao.findById(4L);

        assertAll(
                "author",
                () -> assertNotNull(author_selected),
                () -> assertEquals(4, author_selected.orElse(null).getId()),
                () -> assertEquals(author.getSurname(), author_selected.orElse(null).getSurname()),
                () -> assertEquals(author.getFirstName(), author_selected.orElse(null).getFirstName()),
                () -> assertEquals(author.getSecondName(), author_selected.orElse(null).getSecondName())
        );
    }

    @Test
    @DisplayName("Удаление автора, который используется в таблице книг")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.deleteById(1L));
        assertThat(authorDao.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Обновление автора")
    void updateObject() {
        authorDao.save(
                new Author(1L, "TestSurname", "TestFirstName", "TestSecondName")
        );
        Author author = authorDao.findById(1L).orElse(null);

        assertAll(
                "author",
                () -> assertEquals(1L, author.getId()),
                () -> assertEquals("TestSurname", author.getSurname()),
                () -> assertEquals("TestFirstName", author.getFirstName()),
                () -> assertEquals("TestSecondName", author.getSecondName())
        );
    }
}