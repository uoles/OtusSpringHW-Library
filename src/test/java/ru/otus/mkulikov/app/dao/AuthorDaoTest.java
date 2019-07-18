package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DataMongoTest
@DisplayName("Класс AuthorDao")
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @BeforeEach
    void init() {
        authorDao.save(getAuthor(1L));
        authorDao.save(getAuthor(2L));
        authorDao.save(getAuthor(3L));
    }

    @Test
    @DisplayName("Получение автора по id")
    void getById() {
        Optional<Author> author = authorDao.findById(1L);

        assertThat(author).isNotEmpty();
        assertThat(author).contains(getAuthor(1L));
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAllObjects() {
        List<Author> authors = authorDao.findAll();

        assertThat(authors).size().isEqualTo(3);
        assertThat(authors).containsAll(getAuthors());
    }

    @Test
    @DisplayName("Добавление автора")
    void addObject() {
        Author author = authorDao.save(getAuthor(4L));
        Optional<Author> author_selected = authorDao.findById(author.getId());

        assertThat(author_selected).isNotEmpty();
        assertThat(author_selected).contains(getAuthor(4L));
    }

    @Test
    @DisplayName("Удаление автора")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
//        assertThrows(DataIntegrityViolationException.class, () -> authorDao.deleteById(1L));

        assertThat(authorDao.count()).isEqualTo(3);
        authorDao.deleteById(1L);
        assertThat(authorDao.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Обновление автора")
    void updateObject() {
        Author author =  new Author(1L, "TestSurname", "TestFirstName", "TestSecondName");
        authorDao.save(author);
        Optional<Author> author_selected = authorDao.findById(1L);

        assertThat(author_selected).isNotEmpty();
        assertThat(author_selected).contains(author);
    }

    private Author getAuthor(long id) {
        return new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
    }

    private List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(getAuthor(1L));
        authors.add(getAuthor(2L));
        authors.add(getAuthor(3L));
        return authors;
    }
}