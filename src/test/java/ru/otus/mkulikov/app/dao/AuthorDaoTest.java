package ru.otus.mkulikov.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthorList;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 10:12
 */

@DataMongoTest
@DisplayName("Класс AuthorDao")
class AuthorDaoTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";
    private final String ID_3 = "3";
    private final String ID_4 = "4";

    private final int OBJECT_COUNT_3 = 3;
    private final int OBJECT_COUNT_2 = 2;

    private final String TEST_SURNAME = "TestSurname";
    private final String TEST_FIRST_NAME = "TestFirstName";
    private final String TEST_SECOND_NAME = "TestSecondName";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorDao authorDao;

    @BeforeEach
    void init() {
        mongoTemplate.save(getAuthor(ID_1));
        mongoTemplate.save(getAuthor(ID_2));
        mongoTemplate.save(getAuthor(ID_3));
    }

    @Test
    @DisplayName("Получение автора по id")
    void getById() {
        Optional<Author> author = authorDao.findById(ID_1);

        assertThat(author).isNotEmpty();
        assertThat(author).contains(getAuthor(ID_1));
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAllObjects() {
        List<Author> authors = authorDao.findAll();

        assertThat(authors).size().isEqualTo(OBJECT_COUNT_3);
        assertThat(authors).containsAll(getAuthorList());
    }

    @Test
    @DisplayName("Добавление автора")
    void addObject() {
        Author author = authorDao.save(getAuthor(ID_4));
        Optional<Author> author_selected = authorDao.findById(author.getId());

        assertThat(author_selected).isNotEmpty();
        assertThat(author_selected).contains(getAuthor(ID_4));
    }

    @Test
    @DisplayName("Удаление автора")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteObject() {
        assertThat(authorDao.count()).isEqualTo(OBJECT_COUNT_3);
        authorDao.deleteById(ID_1);
        assertThat(authorDao.count()).isEqualTo(OBJECT_COUNT_2);
    }

    @Test
    @DisplayName("Обновление автора")
    void updateObject() {
        Author author = new Author(ID_1, TEST_SURNAME, TEST_FIRST_NAME, TEST_SECOND_NAME);
        authorDao.save(author);
        Optional<Author> author_selected = authorDao.findById(ID_1);

        assertThat(author_selected).isNotEmpty();
        assertThat(author_selected).contains(author);
    }
}