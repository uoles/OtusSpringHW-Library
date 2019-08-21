package ru.otus.mkulikov.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.CommentManageService;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentManageService commentManageService;

    @GetMapping("/list/comments")
    public String listPage(Model model) {
        List<Comment> comments = commentManageService.getComments();
        model.addAttribute("comments", comments);
        return "comments";
    }
}
