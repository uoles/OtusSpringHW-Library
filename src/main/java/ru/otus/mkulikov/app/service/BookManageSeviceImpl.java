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
public class BookManageSeviceImpl implements BookManageSevice {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id).get();
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAllObjects();
    }

    @Override
    public long addBook(String caption, long authorId, long genreId, String description) {
        Author author = authorDao.findById(authorId).get();
        Genre genre = genreDao.findById(genreId).get();

        Book book = bookDao.save(new Book(caption, author, genre, description));
        return book.getId();
    }

    @Override
    public int updateBook(long id, String caption, long authorId, long genreId, String description) {
        Author author = authorDao.findById(authorId).get();
        Genre genre = genreDao.findById(genreId).get();

        Book book = bookDao.findById(id).get();
        book.setCaption(caption);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setGenre(genre);
        book.setDescription(description);

        bookDao.save(book);
        return 1;
    }

    @Override
    public int deleteBook(long id) {
        bookDao.deleteById(id);
        bookDao.count();
        return 1;
    }
}
