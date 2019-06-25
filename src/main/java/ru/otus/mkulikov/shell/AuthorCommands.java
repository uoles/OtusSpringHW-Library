package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.service.AuthorManageService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 14:46
 */

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorManageService authorManageService;

    @ShellMethod(key = {"getAuthorById"}, value = "Select author by id.")
    public String getAuthorById(@ShellOption long id) {
        Author author = authorManageService.getAuthorById(id);
        return author.toString();
    }

    @ShellMethod(key = {"getAuthors"}, value = "Select all authors.")
    public String getAuthors() {
        List<Author> allObjects = authorManageService.getAuthors();
        return allObjects.toString();
    }

    @ShellMethod(key = {"addAuthor"}, value = "Add new author.")
    public String addAuthor(@ShellOption String surname, @ShellOption String firstName, @ShellOption String secondName) {
        long id = authorManageService.addAuthor(surname, firstName, secondName);
        return "Add " + (id != 0 ? 1 : 0) + " row(s)";
    }

    @ShellMethod(key = {"updateAuthor"}, value = "Update author by id.")
    public String updateAuthor(@ShellOption long id, @ShellOption String surname, @ShellOption String firstName, @ShellOption String secondName) {
        int count = authorManageService.updateAuthor(id, surname, firstName, secondName);
        return "Updated " + count + " row(s)";
    }

    @ShellMethod(key = {"deleteAuthor"}, value = "Delete author by id.")
    public String deleteAuthor(@ShellOption long id) {
        int count = authorManageService.deleteAuthor(id);
        return "Deleted " + count + " row(s)";
    }
}
