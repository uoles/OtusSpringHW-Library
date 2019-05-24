package ru.otus.mkulikov.shell;

import org.jline.reader.LineReader;
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
    private LineReader reader;

    @Autowired
    public Commands(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    public String write(String text) {
        return this.reader.readLine("\n" + text + " > ");
    }

    @ShellMethod(key = { "getBookById" }, value = "Select book by id.")
    public String getBookById(@ShellOption String id) {
        Book book = bookDao.getById(Integer.parseInt(id));
        return book.toString();
    }

    @ShellMethod(key = { "setService", "select" }, value = "Choose a Speech to Text Service")
    public void setService() {
        boolean success = false;
        do {
            String question = "Please select a service.";

            // Get Input
            String input = this.write(question);
            if ("1".equals(input)) {
                this.write("OK");
            } else {
                this.write("NO");
            }

            // Input handling
            /*
             * do something with input variable
             */
            success = true;

        } while (!success);
    }
}
