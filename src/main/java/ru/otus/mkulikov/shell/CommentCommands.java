package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.service.CommentManageService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 14:47
 */

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    private final CommentManageService commentManageService;

    @ShellMethod(key = {"getCommentById"}, value = "Select comment by id.")
    public String getCommentById(@ShellOption String id) {
        Comment comment = commentManageService.getCommentById(id);
        return comment.toString();
    }

    @ShellMethod(key = {"getComments"}, value = "Select all comments.")
    public String getComments() {
        List<Comment> allObjects = commentManageService.getComments();
        return allObjects.toString();
    }

    @ShellMethod(key = {"getCommentsByBookId"}, value = "Select all comments.")
    public String getCommentsByBookId(@ShellOption String bookId) {
        List<Comment> allObjects = commentManageService.getCommentsByBookId(bookId);
        return allObjects.toString();
    }

    @ShellMethod(key = {"addComment"}, value = "Add new comment.")
    public String addComment(@ShellOption String bookId, @ShellOption String userName, @ShellOption String text) {
        Comment comment = commentManageService.addComment(bookId, userName, text);
        return "Add " + (comment != null ? 1 : 0) + " row(s)\nID: " + comment.getId();
    }

    @ShellMethod(key = {"updateComment"}, value = "Update comment by id.")
    public String updateComment(@ShellOption String id, @ShellOption String userName, @ShellOption String text) {
        Comment comment = commentManageService.updateComment(id, userName, text);
        return "Updated " + (comment != null ? 1 : 0) + " row(s)\nID: " + comment.getId();
    }

    @ShellMethod(key = {"deleteComment"}, value = "Delete comment by id.")
    public String deleteComment(@ShellOption String id) {
        int count = commentManageService.deleteComment(id);
        return "Deleted " + count + " row(s)";
    }
}
