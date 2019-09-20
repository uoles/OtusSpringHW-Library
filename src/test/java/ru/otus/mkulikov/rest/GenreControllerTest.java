package ru.otus.mkulikov.rest;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenre;

@RunWith(SpringRunner.class)
@DisplayName("Класс rest-контроллеров для жанров")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @MockBean
    private BookManageService bookManageService;
    @MockBean
    private GenreManageService genreManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех жанров")
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/genre/list")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Genres:")));
    }

    @Test
    @DisplayName("Получение жанра по id")
    public void getById() throws Exception {
        when(genreManageService.getGenreById(anyString())).thenReturn(getGenre("1"));

        this.mockMvc.perform(get("/genre").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Genre detail:")));
    }

    @Test
    @DisplayName("Добавление жанра")
    public void addGenre() throws Exception {
        when(genreManageService.addGenre(anyString())).thenReturn(getGenre("1"));

        this.mockMvc.perform(get("/genre/new"))
                .andDo(print())
                .andExpect(redirectedUrl("/genre?id=1"));
    }

    @Test
    @DisplayName("Изменение жанра")
    public void edit() throws Exception {
        when(genreManageService.updateGenre(any(Genre.class))).thenReturn(getGenre("1"));

        this.mockMvc.perform(post("/genre/edit").requestAttr("Genre", getGenre("1")))
                .andDo(print())
                .andExpect(redirectedUrl("/genre/list"));
    }

    @Test
    @DisplayName("Удаление жанра")
    public void delete() throws Exception {
        when(genreManageService.deleteGenre(anyString())).thenReturn(1);

        this.mockMvc.perform(post("/genre/delete").param("id", "1"))
                .andDo(print())
                .andExpect(redirectedUrl("/genre/list"));
    }
}
