package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.BookManageSevice;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookManageSevice bookManageSevice;

    @GetMapping("/list/books")
    public String listPage(Model model) {
        List<Book> books = bookManageSevice.getBooks();
        model.addAttribute("books", books);
        return "books";
    }
}
