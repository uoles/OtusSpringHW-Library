package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.BookManageSevice;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreManageService genreManageService;

    @GetMapping("/list/genres")
    public String getAll(Model model) {
        List<Genre> genres = genreManageService.getGenres();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genre")
    public String getById(@RequestParam("id") String id, Model model) {
        Genre genre = genreManageService.getGenreById(id);
        model.addAttribute("genre", genre);
        return "genre";
    }
}
