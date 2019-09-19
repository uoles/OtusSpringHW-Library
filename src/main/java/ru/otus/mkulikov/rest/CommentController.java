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
import ru.otus.mkulikov.app.service.BookManageService;
import ru.otus.mkulikov.app.service.CommentManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final BookManageService bookManageSevice;
    private final CommentManageService commentManageService;

    @GetMapping("/comment/list")
    public String getAll(Model model) {
        List<Comment> comments = commentManageService.getComments();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comment")
    public String getById(@RequestParam("id") String id, Model model) {
        List<Book> books = bookManageSevice.getBooks();
        Comment comment = commentManageService.getCommentById(id);

        model.addAttribute("books", books);
        model.addAttribute("comment", comment);

        return "comment";
    }

    @PostMapping(value = "/comment/edit")
    public String edit(@ModelAttribute Comment comment, Model model) {
        List<Book> books = bookManageSevice.getBooks();
        Comment updated = commentManageService.updateComment(comment);

        model.addAttribute("books", books);
        model.addAttribute("comment", updated);

        return "comment";
    }

    @GetMapping("/comment/new")
    public String getNew(Model model) {
        List<Book> books = bookManageSevice.getBooks();

        String bookId = (books != null && !books.isEmpty())
                ? books.get(0).getId()
                : "";
        Comment comment = commentManageService.addComment(bookId, "", "");

        model.addAttribute("books", books);
        model.addAttribute("comment", comment);

        return "comment";
    }

    @PostMapping(value = "/comment/delete")
    public String delete(@RequestParam("id") String id) {
        commentManageService.deleteComment(id);
        return "redirect:/comment/list";
    }
}
