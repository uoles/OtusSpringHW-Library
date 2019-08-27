package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageSevice;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookManageSevice bookManageSevice;

    @GetMapping("/list/books")
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

    @PostMapping(value="/book/edit")
    public String bookEdit(@ModelAttribute Book book, Model model) {
        Book orig = bookManageSevice.getBookById(book.getId());
        orig.setCaption(book.getCaption());
        orig.setDescription(book.getDescription());

        Book updated = bookManageSevice.updateBook(orig);
        model.addAttribute("book", updated);
        return "book";
    }
}
