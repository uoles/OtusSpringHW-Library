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
        Comment comment = commentManageService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "comment";
    }

    @PostMapping(value = "/comment/edit")
    public String edit(@ModelAttribute Comment comment, Model model) {
        Comment updated = commentManageService.updateComment(comment);
        model.addAttribute("comment", updated);
        return "comment";
    }

    @GetMapping("/comment/new")
    public String getNew(Model model) {
        List<Book> books = bookManageSevice.getBooks();
        Comment comment = commentManageService.addComment("", "", "");

        model.addAttribute("books", books);
        model.addAttribute("comment", comment);

        return "comment";
    }
}
