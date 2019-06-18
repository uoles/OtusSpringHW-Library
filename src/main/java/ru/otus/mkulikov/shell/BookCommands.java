package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.service.BookManageSevice;

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

    private final BookManageSevice bookManageSevice;

    @ShellMethod(key = {"getBookById"}, value = "Select book by id.")
    public String getBookById(@ShellOption long id) {
        Book book = bookManageSevice.getBookById(id);
        return book.toString();
    }

    @ShellMethod(key = {"getBooks"}, value = "Select all books.")
    public String getBooks() {
        List<Book> allObjects = bookManageSevice.getBooks();
        return allObjects.toString();
    }

    @ShellMethod(key = {"addBook"}, value = "Add new book.")
    public String addBook(@ShellOption String caption, @ShellOption int authorId, @ShellOption int genreId, @ShellOption String comment) {
        int count = bookManageSevice.addBook(caption, authorId, genreId, comment);
        return "Add " + count + " row(s)";
    }

    @ShellMethod(key = {"updateBook"}, value = "Update book by id.")
    public String updateBook(@ShellOption long id, @ShellOption String caption, @ShellOption int authorId, @ShellOption int genreId, @ShellOption String comment) {
        int count = bookManageSevice.updateBook(id, caption, authorId, genreId, comment);
        return "Updated " + count + " row(s)";
    }

    @ShellMethod(key = {"deleteBook"}, value = "Delete book by id.")
    public String deleteBook(@ShellOption long id) {
        int count = bookManageSevice.deleteBook(id);
        return "Deleted " + count + " row(s)";
    }
}
