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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DisplayName("Класс rest-контроллеров для автора")
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    private final String SURNAME = "Surname";
    private final String FIRST_NAME = "FirstName";
    private final String SECOND_NAME = "SecondName";

    @MockBean
    private AuthorManageService authorManageService;
    @MockBean
    private BookManageService bookManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех авторов")
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/author/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Authors:")));
    }

    @Test
    @DisplayName("Получение автора по id")
    public void getById() throws Exception {
        when(authorManageService.getAuthorById(anyString())).thenReturn(getAuthor("1"));

        this.mockMvc.perform(get("/author").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Author detail:")));
    }

//    @Test
//    @DisplayName("Редактирование автора")
//    public void edit() throws Exception {
//        this.mockMvc.perform(get("/author/edit"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Authors:")));
//    }

    private Author getAuthor(String id) {
        return new Author(id, SURNAME + id, FIRST_NAME + id, SECOND_NAME + id);
    }
}