package ru.otus.mkulikov.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.dao.AuthorDao;
import ru.otus.mkulikov.dao.BookDao;
import ru.otus.mkulikov.dao.GenreDao;
import ru.otus.mkulikov.model.Book;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:33
 */

@ShellComponent
public class Commands {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    @Autowired
    public Commands(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(key = { "getBookById" }, value = "Select book by id.")
    public String getBookById(@ShellOption String id) {
        Book book = bookDao.getById(Integer.parseInt(id));
        return book.toString();
    }
}
