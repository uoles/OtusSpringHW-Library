package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.model.Author;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DisplayName("Класс AuthorDaoJpa")
@RunWith(SpringRunner.class)
@ComponentScan("ru.otus.mkulikov.app")
@DataJpaTest
@TestPropertySource(locations= "classpath:application.yml")
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Получение автора по id")
    void getById() {
        Optional<Author> author = authorDao.findById(1L);

        assertAll(
                "author",
                () -> assertNotNull(author.get()),
                () -> assertEquals(1L, author.get().getId()),
                () -> assertEquals("Surname", author.get().getSurname()),
                () -> assertEquals("FirstName", author.get().getFirstName()),
                () -> assertEquals("SecondName", author.get().getSecondName())
        );
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAllObjects() {
        Iterable<Author> authors = authorDao.findAll();
        List<Author> list = StreamSupport
                .stream(authors.spliterator(), false)
                .collect(Collectors.toList());

        assertAll(
                "authors",
                () -> assertNotNull(list),
                () -> assertEquals(3, list.size()),
                () -> assertEquals("Surname", list.get(0).getSurname()),
                () -> assertEquals("Surname2", list.get(1).getSurname()),
                () -> assertEquals("Surname3", list.get(2).getSurname())
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
                () -> assertEquals(4, author_selected.get().getId()),
                () -> assertEquals(author.getSurname(), author_selected.get().getSurname()),
                () -> assertEquals(author.getFirstName(), author_selected.get().getFirstName()),
                () -> assertEquals(author.getSecondName(), author_selected.get().getSecondName())
        );
    }

    @Test
    @DisplayName("Удаление автора, который используется в таблице книг")
    void deleteObject() {
        authorDao.deleteById(1L);
        Author author = authorDao.findById(1L).get();

//        assertNotNull(author);
    }

    @Test
    @DisplayName("Обновление автора")
    void updateObject() {
        authorDao.save(
                new Author(1L, "TestSurname", "TestFirstName", "TestSecondName")
        );
        Author author = authorDao.findById(1L).get();

        assertAll(
                "author",
                () -> assertEquals(1L, author.getId()),
                () -> assertEquals("TestSurname", author.getSurname()),
                () -> assertEquals("TestFirstName", author.getFirstName()),
                () -> assertEquals("TestSecondName", author.getSecondName())
        );
    }
}