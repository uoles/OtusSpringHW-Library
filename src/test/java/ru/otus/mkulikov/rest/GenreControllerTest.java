package ru.otus.mkulikov.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mkulikov.app.service.GenreManageService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @MockBean
    private GenreManageService genreManageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/genre/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Genres:")));
    }

    @Test
    public void getById() throws Exception {
        this.mockMvc.perform(get("/genre")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void edit() throws Exception {
        this.mockMvc.perform(get("/genre/edit")).andDo(print()).andExpect(status().isMethodNotAllowed());
    }
}