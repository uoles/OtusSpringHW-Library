package ru.otus.mkulikov.app.service;

import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:59
 */

@DisplayName("Класс AuthorManageSevice")
@TestPropertySource(locations= "classpath:application.yml")
@ExtendWith(MockitoExtension.class)
class AuthorManageSeviceImplTest {

    @Mock
    private AuthorDao authorDao;

    @InjectMocks
    private AuthorManageServiceImpl authorManageService;

    @Test
    @DisplayName("Получение автора по id")
    void getAuthorById() {
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(1L)) );
        Author author = authorManageService.getAuthorById(1L);

        assertThat(author).isNotNull();
        assertThat(author).isEqualTo(getAuthor(1L));
    }

    @Test
    @DisplayName("Получение всех авторов")
    void getAuthors() {
        when(authorDao.findAll()).thenReturn( getAuthorList() );
        List<Author> authors = authorManageService.getAuthors();

        assertThat(authors).isNotNull();
        assertThat(authors).hasSize(3);
        assertThat(authors).containsAll(getAuthorList());
    }

    @Test
    @DisplayName("Добавление автора")
    void addAuthor() {
        when(authorDao.save(any(Author.class))).then(new Answer<Author>() {
            int sequence = 1;

            @Override
            public Author answer(InvocationOnMock invocationOnMock) throws Throwable {
                Author author = (Author) invocationOnMock.getArgument(0);
                author.setId(++sequence);
                return author;
            }
        });
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(2L)) );

        long id = authorManageService.addAuthor("Surname1", "FirstName1", "SecondName1");
        Author author = authorManageService.getAuthorById(id);

        assertThat(id).isEqualTo(2L);
        assertThat(author).isNotNull();
        assertThat(author).isEqualTo(getAuthor(2L));
    }

    @Test
    @Ignore
    @DisplayName("Обновление автора")
    void updateAuthor() {
        when(authorDao.save(any(Author.class))).then(new Answer<Author>() {

            @Override
            public Author answer(InvocationOnMock invocationOnMock) throws Throwable {
                Author author = (Author) invocationOnMock.getArgument(0);
                author.setId(1L);
                return author;
            }
        });
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(1L)) );

        int count = authorManageService.updateAuthor(1L, "Surname1", "FirstName1", "SecondName1");
        Author author = authorManageService.getAuthorById(1L);

        assertThat(count).isEqualTo(1);
        assertThat(author).isNotNull();
        assertThat(author).isEqualTo(getAuthor(1L));
    }

    @Test
    @Ignore
    @DisplayName("Удаление автора, который используется в таблице книг")
    void deleteAuthor() {
        doThrow(DataIntegrityViolationException.class).when(authorDao).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> { authorManageService.deleteAuthor(1L); });
    }

    private List<Author> getAuthorList() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(getAuthor(1L));
        authors.add(getAuthor(2L));
        authors.add(getAuthor(3L));
        return authors;
    }

    private Author getAuthor(long id) {
        return new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
    }
}
