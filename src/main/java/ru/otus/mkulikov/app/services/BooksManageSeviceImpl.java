package ru.otus.mkulikov.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 27.05.2019
 * Time: 13:42
 */

@Service
@RequiredArgsConstructor
public class BooksManageSeviceImpl implements BooksManageSevice {

    private final AuthorDao<Author> authorDao;
    private final BookDao<Book> bookDao;
    private final GenreDao<Genre> genreDao;

    public Book getBookById(int id) {
        return bookDao.getById(id);
    }

    public List<Book> getBooks() {
        return bookDao.getAllObjects();
    }

    public Author getAuthorById(int id) {
        return authorDao.getById(id);
    }

    public List<Author> getAuthors() {
        return authorDao.getAllObjects();
    }

    public Genre getGenreById(int id) {
        return genreDao.getById(id);
    }

    public List<Genre> getGenres() {
        return genreDao.getAllObjects();
    }

    public int deleteGenre(int id) {
        return genreDao.deleteObject(id);
    }
}
