package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final BookManageService bookManageService;
    private final GenreManageService genreManageService;

    @GetMapping("/genre/list")
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

    @GetMapping("/genre/new")
    public String getNew() {
        Genre genre = genreManageService.addGenre("");
        return "redirect:/genre?id=" + genre.getId();
    }

    @PostMapping(value = "/genre/edit")
    public String edit(@ModelAttribute Genre genre, Model model) {
        Genre updated = genreManageService.updateGenre(genre);
        model.addAttribute("genre", updated);
        return "redirect:/genre/list";
    }

    @PostMapping(value = "/genre/delete")
    public String delete(@RequestParam("id") String id) {
        List<Book> books = bookManageService.getBookByGenreId(id);
        if (books == null || (books != null && books.isEmpty())) {
            genreManageService.deleteGenre(id);
        }
        return "redirect:/genre/list";
    }
}
