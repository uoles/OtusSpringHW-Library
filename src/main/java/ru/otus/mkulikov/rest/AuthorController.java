package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorManageService authorManageService;
    private final BookManageService bookManageService;

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

    @GetMapping("/author/new")
    public String getNew() {
        Author author = authorManageService.addAuthor("", "", "");
        return "redirect:/author?id=" + author.getId();
    }

    @PostMapping(value = "/author/edit")
    public String edit(@ModelAttribute Author author) {
        Author updated = authorManageService.updateAuthor(author);
        return "redirect:/author?id=" + updated.getId();
    }

    @PostMapping(value = "/author/delete")
    public String delete(@RequestParam("id") String id) {
        List<Book> books = bookManageService.getBookByAuthorId(id);
        if (books == null || (books != null && books.isEmpty())) {
            authorManageService.deleteAuthor(id);
        }
        return "redirect:/author/list";
    }
}
