package ru.otus.mkulikov.rest;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.CommentManageService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.mkulikov.generators.GenerateBook.getBooksList;
import static ru.otus.mkulikov.generators.GenerateComment.getComment;

@RunWith(SpringRunner.class)
@DisplayName("Класс rest-контроллеров для комментариев")
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockBean
    private BookManageService bookManageService;
    @MockBean
    private CommentManageService commentManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех комментариев")
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/comment/list")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Comments:")));
    }

    @Test
    @DisplayName("Получение комментария по id")
    public void getById() throws Exception {
        when(commentManageService.getCommentById(anyString())).thenReturn(getComment("1", "1"));
        when(bookManageService.getBooks()).thenReturn(getBooksList());

        this.mockMvc.perform(get("/comment").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Comment detail:")));
    }

    @Test
    @DisplayName("Изменение комментария")
    public void edit() throws Exception {
        when(bookManageService.getBooks()).thenReturn(getBooksList());
        when(commentManageService.updateComment(any(Comment.class))).thenReturn(getComment("1", "1"));

        this.mockMvc.perform(post("/comment/edit").requestAttr("Comment", getComment("1", "1")))
                .andDo(print())
                .andExpect(redirectedUrl("/comment/list"));
    }

    @Test
    @DisplayName("Добавление комментария")
    public void addComment() throws Exception {
        when(bookManageService.getBooks()).thenReturn(getBooksList());
        when(commentManageService.addComment(anyString(), anyString(), anyString())).thenReturn(getComment("1", "1"));

        this.mockMvc.perform(get("/comment/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Comment detail:")));
    }

    @Test
    @DisplayName("Удаление комментария")
    public void delete() throws Exception {
        when(commentManageService.deleteComment(anyString())).thenReturn(1);

        this.mockMvc.perform(post("/comment/delete").param("id", "1"))
                .andDo(print())
                .andExpect(redirectedUrl("/comment/list"));
    }
}
