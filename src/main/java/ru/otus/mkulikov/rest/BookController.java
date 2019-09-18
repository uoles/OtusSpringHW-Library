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
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookManageService bookManageSevice;
    private final AuthorManageService authorManageService;
    private final GenreManageService genreManageService;

    @GetMapping("/book/list")
    public String getAll(Model model) {
        List<Book> books = bookManageSevice.getBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String getById(@RequestParam("id") String id, Model model) {
        Book book = bookManageSevice.getBookById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping(value = "/book/edit")
    public String edit(@ModelAttribute Book book, Model model) {
        Book updated = bookManageSevice.updateBook(book);
        model.addAttribute("book", updated);
        return "book";
    }

    @GetMapping("/book/new")
    public String getNew() {
        List<Author> listAuthor = authorManageService.getAuthors();
        Book book = bookManageSevice.addBook("", "", "", "");
        return "redirect:/book?id=" + book.getId();
    }
}
