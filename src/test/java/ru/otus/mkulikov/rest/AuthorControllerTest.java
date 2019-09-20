package ru.otus.mkulikov.rest;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthorList;
import static ru.otus.mkulikov.generators.GenerateBook.getBooksList;

@RunWith(SpringRunner.class)
@DisplayName("Класс rest-контроллеров для автора")
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @MockBean
    private AuthorManageService authorManageService;
    @MockBean
    private BookManageService bookManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех авторов")
    public void getAll() throws Exception {
        when(authorManageService.getAuthors()).thenReturn(getAuthorList());

        this.mockMvc.perform(get("/author/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Authors:")));
    }

    @Test
    @DisplayName("Получение автора по id")
    public void getAuthorById() throws Exception {
        when(authorManageService.getAuthorById(anyString())).thenReturn(getAuthor("1"));

        this.mockMvc.perform(get("/author").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Author detail:")));
    }

    @Test
    @DisplayName("Добавление автора")
    public void addAuthor() throws Exception {
        when(authorManageService.addAuthor(anyString(), anyString(), anyString())).thenReturn(getAuthor("1"));

        this.mockMvc.perform(get("/author/new"))
                .andDo(print())
                .andExpect(redirectedUrl("/author?id=1"));
    }

    @Test
    @DisplayName("Изменение автора")
    public void edit() throws Exception {
        when(authorManageService.updateAuthor(any(Author.class))).thenReturn(getAuthor("1"));

        this.mockMvc.perform(post("/author/edit").requestAttr("Author", getAuthor("1")))
                .andDo(print())
                .andExpect(redirectedUrl("/author/list"));
    }

    @Test
    @DisplayName("Удаление автора")
    public void delete() throws Exception {
        when(bookManageService.getBookByAuthorId(anyString())).thenReturn(getBooksList());
        when(authorManageService.deleteAuthor(anyString())).thenReturn(1);

        this.mockMvc.perform(post("/author/delete").param("id", "1"))
                .andDo(print())
                .andExpect(redirectedUrl("/author/list"));
    }
}
