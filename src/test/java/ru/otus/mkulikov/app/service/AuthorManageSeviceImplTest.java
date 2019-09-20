package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:59
 */

@DisplayName("Класс AuthorManageSevice")
@ExtendWith(MockitoExtension.class)
class AuthorManageSeviceImplTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";
    private final String ID_3 = "3";

    private final int OBJECT_COUNT_3 = 3;

    private final String SURNAME = "Surname";
    private final String FIRST_NAME = "FirstName";
    private final String SECOND_NAME = "SecondName";

    @Mock
    private AuthorDao authorDao;

    @InjectMocks
    private AuthorManageServiceImpl authorManageService;

    @Test
    @DisplayName("Получение автора по id")
    void getAuthorById() {
        when(authorDao.findById(anyString())).thenReturn(Optional.of(getAuthor(ID_1)));
        Author author = authorManageService.getAuthorById(ID_1);

        assertThat(author).isNotNull();
        assertThat(author).isEqualTo(getAuthor(ID_1));
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAuthors() {
        when(authorDao.findAll()).thenReturn(getAuthorList());
        List<Author> authors = authorManageService.getAuthors();

        assertThat(authors).isNotNull();
        assertThat(authors).hasSize(OBJECT_COUNT_3);
        assertThat(authors).containsAll(getAuthorList());
    }

    @Test
    @DisplayName("Добавление автора")
    void addAuthor() {
        when(authorDao.save(any(Author.class))).then(new Answer<Author>() {
            @Override
            public Author answer(InvocationOnMock invocationOnMock) throws Throwable {
                Author author = (Author) invocationOnMock.getArgument(0);
                author.setId("2");
                return author;
            }
        });
        when(authorDao.findById(anyString())).thenReturn(Optional.of(getAuthor(ID_2)));

        Author author1 = authorManageService.addAuthor("Surname1", "FirstName1", "SecondName1");
        Author author2 = authorManageService.getAuthorById(author1.getId());

        assertThat(author1.getId()).isEqualTo(ID_2);
        assertThat(author2).isNotNull();
        assertThat(author2).isEqualTo(getAuthor(ID_2));
    }

    @Test
    @DisplayName("Обновление автора")
    void updateAuthor() {
        when(authorDao.save(any(Author.class))).then(new Answer<Author>() {

            @Override
            public Author answer(InvocationOnMock invocationOnMock) throws Throwable {
                Author author = (Author) invocationOnMock.getArgument(0);
                author.setId(ID_1);
                return author;
            }
        });
        when(authorDao.findById(anyString())).thenReturn(Optional.of(getAuthor(ID_1)));

        Author author1 = authorManageService.updateAuthor(ID_1, "Surname1", "FirstName1", "SecondName1");
        Author author2 = authorManageService.getAuthorById(ID_1);

        assertThat(author1).isNotNull();
        assertThat(author2).isEqualTo(getAuthor(ID_1));
    }

    @Test
    @DisplayName("Удаление автора, который используется в таблице книг")
    void deleteAuthor() {
        doThrow(DataIntegrityViolationException.class).when(authorDao).deleteById(ID_1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            authorManageService.deleteAuthor(ID_1);
        });
    }

    private List<Author> getAuthorList() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(getAuthor(ID_1));
        authors.add(getAuthor(ID_2));
        authors.add(getAuthor(ID_3));
        return authors;
    }

    private Author getAuthor(String id) {
        return new Author(id, SURNAME + id, FIRST_NAME + id, SECOND_NAME + id);
    }
}
