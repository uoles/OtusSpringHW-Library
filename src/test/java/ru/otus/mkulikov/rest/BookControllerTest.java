package ru.otus.mkulikov.rest;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.CommentManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthorList;
import static ru.otus.mkulikov.generators.GenerateBook.getBook;
import static ru.otus.mkulikov.generators.GenerateComment.getCommentList;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenreList;

@RunWith(SpringRunner.class)
@DisplayName("Класс rest-контроллеров для книг")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookManageService bookManageService;
    @MockBean
    private AuthorManageService authorManageService;
    @MockBean
    private GenreManageService genreManageService;
    @MockBean
    private CommentManageService commentManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех книг")
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/book/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Books:")));
    }

    @Test
    @DisplayName("Получение книги по id")
    public void getById() throws Exception {
        when(bookManageService.getBookById(anyString())).thenReturn(getBook("1"));
        when(authorManageService.getAuthors()).thenReturn(getAuthorList());
        when(genreManageService.getGenres()).thenReturn(getGenreList());

        this.mockMvc.perform(get("/book").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book detail:")));
    }

    @Test
    @DisplayName("Добавление книги")
    public void addBook() throws Exception {
        when(bookManageService.addBook(anyString(), anyString(), anyString(), anyString())).thenReturn(getBook("1"));
        when(authorManageService.getAuthors()).thenReturn(getAuthorList());
        when(genreManageService.getGenres()).thenReturn(getGenreList());

        this.mockMvc.perform(get("/book/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book detail:")));
    }

    @Test
    @DisplayName("Изменение книги")
    public void edit() throws Exception {
        when(bookManageService.updateBook(any(Book.class))).thenReturn(getBook("1"));

        this.mockMvc.perform(post("/book/edit").requestAttr("Book", getBook("1")))
                .andDo(print())
                .andExpect(redirectedUrl("/book/list"));
    }

    @Test
    @DisplayName("Удаление книги")
    public void delete() throws Exception {
        when(commentManageService.getCommentsByBookId(anyString())).thenReturn(getCommentList("1"));
        when(commentManageService.deleteComments(getCommentList())).thenReturn(1);
        when(bookManageService.deleteBook(anyString())).thenReturn(1);

        this.mockMvc.perform(post("/book/delete").param("id", "1"))
                .andDo(print())
                .andExpect(redirectedUrl("/book/list"));
    }
}
