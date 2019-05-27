package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.services.BooksManageSevice;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:33
 */

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final BooksManageSevice booksManageSevice;

    /**************
     * Жанры
     */

    @ShellMethod(key = { "getGenreById" }, value = "Select genre by id.")
    public String getGenreById(@ShellOption int id) {
        Genre genre = booksManageSevice.getGenreById(id);
        return genre.toString();
    }

    @ShellMethod(key = { "getGenres" }, value = "Select all genres.")
    public String getGenres() {
        List<Genre> allObjects = booksManageSevice.getGenres();
        return allObjects.toString();
    }

    @ShellMethod(key = { "addGenre" }, value = "Add new genre.")
    public String addGenre(@ShellOption String name) {
        int count = booksManageSevice.addGenre(name);
        return "Add " + count + " row(s)";
    }

    @ShellMethod(key = { "updateGenre" }, value = "Update genre by id.")
    public String updateGenre(@ShellOption int id, @ShellOption String name) {
        int count = booksManageSevice.updateGenre(id, name);
        return "Updated " + count + " row(s)";
    }

    @ShellMethod(key = { "deleteGenre" }, value = "Delete genre by id.")
    public String deleteGenre(@ShellOption int id) {
        int count = booksManageSevice.deleteGenre(id);
        return "Deleted " + count + " row(s)";
    }

    /**************
     * Книги
     */

    @ShellMethod(key = { "getBookById" }, value = "Select book by id.")
    public String getBookById(@ShellOption int id) {
        Book book = booksManageSevice.getBookById(id);
        return book.toString();
    }

    @ShellMethod(key = { "getBooks" }, value = "Select all books.")
    public String getBooks() {
        List<Book> allObjects = booksManageSevice.getBooks();
        return allObjects.toString();
    }

    /**************
     * Авторы
     */

    @ShellMethod(key = { "getAuthorById" }, value = "Select author by id.")
    public String getAuthorById(@ShellOption int id) {
        Author author = booksManageSevice.getAuthorById(id);
        return author.toString();
    }

    @ShellMethod(key = { "getAuthors" }, value = "Select all authors.")
    public String getAuthors() {
        List<Author> allObjects = booksManageSevice.getAuthors();
        return allObjects.toString();
    }
}
