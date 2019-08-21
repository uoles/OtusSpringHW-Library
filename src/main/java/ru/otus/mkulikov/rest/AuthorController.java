package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageSevice;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorManageService authorManageService;

    @GetMapping("/list/authors")
    public String listPage(Model model) {
        List<Author> authors = authorManageService.getAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }
}
