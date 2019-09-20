package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.BookManageService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:33
 */

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookManageService bookManageSevice;

    @ShellMethod(key = {"getBookById"}, value = "Select book by id.")
    public String getBookById(@ShellOption String id) {
        Book book = bookManageSevice.getBookById(id);
        return book.toString();
    }

    @ShellMethod(key = {"getBooks"}, value = "Select all books.")
    public String getBooks() {
        List<Book> allObjects = bookManageSevice.getBooks();
        return allObjects.toString();
    }

    @ShellMethod(key = {"addBook"}, value = "Add new book.")
    public String addBook(@ShellOption String caption, @ShellOption String authorId, @ShellOption String genreId, @ShellOption String description) {
        Book book = bookManageSevice.addBook(caption, authorId, genreId, description);
        return "Add " + (book != null ? 1 : 0) + " row(s)\nID: " + book.getId();
    }

    @ShellMethod(key = {"updateBook"}, value = "Update book by id.")
    public String updateBook(@ShellOption String id, @ShellOption String caption, @ShellOption String authorId, @ShellOption String genreId, @ShellOption String description) {
        Book book = bookManageSevice.updateBook(id, caption, authorId, genreId, description);
        return "Updated " + (book != null ? 1 : 0) + " row(s)\nID: " + book.getId();
    }

    @ShellMethod(key = {"deleteBook"}, value = "Delete book by id.")
    public String deleteBook(@ShellOption String id) {
        int count = bookManageSevice.deleteBook(id);
        return "Deleted " + count + " row(s)";
    }
}
