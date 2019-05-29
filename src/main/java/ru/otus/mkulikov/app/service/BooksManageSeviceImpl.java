package ru.otus.mkulikov.app.service;

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

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    @Override
    public Book getBookById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAllObjects();
    }

    @Override
    public Author getAuthorById(int id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.getAllObjects();
    }

    @Override
    public Genre getGenreById(int id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.getAllObjects();
    }

    @Override
    public int addGenre(String name) {
        return genreDao.addObject(new Genre(name));
    }

    @Override
    public int updateGenre(int id, String name) {
        return genreDao.updateObject(new Genre(id, name));
    }

    @Override
    public int deleteGenre(int id) {
        return genreDao.deleteObject(id);
    }

    @Override
    public int addBook(String caption, int authorId, int genreId, String comment) {
        return bookDao.addObject(new Book(caption, authorId, genreId, comment));
    }

    @Override
    public int updateBook(int id, String caption, int authorId, int genreId, String comment) {
        return bookDao.updateObject(new Book(id, caption, authorId, genreId, comment));
    }

    @Override
    public int deleteBook(int id) {
        return bookDao.deleteObject(id);
    }

    @Override
    public int addAuthor(String surname, String firstName, String secondName) {
        return authorDao.addObject(new Author(surname, firstName, secondName));
    }

    @Override
    public int updateAuthor(int id, String surname, String firstName, String secondName) {
        return authorDao.updateObject(new Author(id, surname, firstName, secondName));
    }

    @Override
    public int deleteAuthor(int id) {
        return authorDao.deleteObject(id);
    }
}
