package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.service.AuthorManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorManageService authorManageService;

    @GetMapping("/author/list")
    public String getAll(Model model) {
        List<Author> authors = authorManageService.getAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author")
    public String getById(@RequestParam("id") String id, Model model) {
        Author author = authorManageService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author";
    }

    @PostMapping(value = "/author/edit")
    public String edit(@ModelAttribute Author author, Model model) {
        Author updated = authorManageService.updateAuthor(author);
        model.addAttribute("author", updated);
        return "author";
    }
}
