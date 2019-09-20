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
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.AuthorManageService;
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.CommentManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookManageService bookManageSevice;
    private final AuthorManageService authorManageService;
    private final GenreManageService genreManageService;
    private final CommentManageService commentManageService;

    @GetMapping("/book/list")
    public String getAll(Model model) {
        List<Book> books = bookManageSevice.getBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String getById(@RequestParam("id") String id, Model model) {
        List<Author> authors = authorManageService.getAuthors();
        List<Genre> genres = genreManageService.getGenres();
        Book book = bookManageSevice.getBookById(id);

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "book";
    }

    @PostMapping(value = "/book/edit")
    public String edit(@ModelAttribute Book book, Model model) {
        List<Author> authors = authorManageService.getAuthors();
        List<Genre> genres = genreManageService.getGenres();
        Book updated = bookManageSevice.updateBook(book);

        model.addAttribute("book", updated);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "redirect:/book/list";
    }

    @GetMapping("/book/new")
    public String getNew(Model model) {
        List<Author> authors = authorManageService.getAuthors();
        List<Genre> genres = genreManageService.getGenres();

        String authorId = (authors != null && !authors.isEmpty())
                ? authors.get(0).getId()
                : "";
        String genreId = (genres != null && !genres.isEmpty())
                ? genres.get(0).getId()
                : "";

        Book book = bookManageSevice.addBook("", authorId, genreId, "");

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "book";
    }

    @PostMapping(value = "/book/delete")
    public String delete(@RequestParam("id") String id) {
        List<Comment> comments = commentManageService.getCommentsByBookId(id);
        commentManageService.deleteComments(comments);
        bookManageSevice.deleteBook(id);
        return "redirect:/book/list";
    }
}
