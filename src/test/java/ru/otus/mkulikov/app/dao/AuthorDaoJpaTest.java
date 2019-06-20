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
    //
    //@Test
    //void addObject() {
    //    Author author = new Author("TestSurname", "TestFirstName", "TestSecondName");
    //    int count = authorDao.save(author);
    //    Author author_selected = authorDao.getById(4L);
    //
    //    assertAll(
    //            "author",
    //            () -> assertNotNull(author_selected),
    //            () -> assertEquals(1, count),
    //            () -> assertEquals(4, author_selected.getId()),
    //            () -> assertEquals(author.getSurname(), author_selected.getSurname()),
    //            () -> assertEquals(author.getFirstName(), author_selected.getFirstName()),
    //            () -> assertEquals(author.getSecondName(), author_selected.getSecondName())
    //    );
    //}
    //
    //@Test
    //void deleteObject() {
    //    assertThrows(PersistenceException.class, () -> { authorDao.deleteObject(1L); });
    //}
    //
    //@Test
    //void updateObject() {
    //    Author author1 = authorDao.getById(1L);
    //    int count = authorDao.updateObject(
    //            new Author(1L, "TestSurname", "TestFirstName", "TestSecondName")
    //    );
    //    Author author2 = authorDao.getById(1L);
    //
    //    assertAll(
    //            "author",
    //            () -> assertEquals(1, count),
    //            () -> assertEquals("Surname", author1.getSurname()),
    //            () -> assertEquals("FirstName", author1.getFirstName()),
    //            () -> assertEquals("SecondName", author1.getSecondName()),
    //            () -> assertEquals("TestSurname", author2.getSurname()),
    //            () -> assertEquals("TestFirstName", author2.getFirstName()),
    //            () -> assertEquals("TestSecondName", author2.getSecondName())
    //    );
    //}
}