package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.Date;
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
    public Book getBookById(String id) {
        return bookDao.findById(id).orElse(null);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book addBook(String caption, String authorId, String genreId, String description) {
        Author author = authorDao.findById(authorId).orElse(null);
        Genre genre = genreDao.findById(genreId).orElse(null);

        return bookDao.save(new Book(new Date(), caption, author, genre, description));
    }

    @Override
    public Book updateBook(String id, String caption, String authorId, String genreId, String description) {
        Author author = authorDao.findById(authorId).orElse(null);
        Genre genre = genreDao.findById(genreId).orElse(null);

        Book book = bookDao.findById(id).orElse(null);
        book.setCaption(caption);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setGenre(genre);
        book.setDescription(description);

        return bookDao.save(book);
    }

    @Override
    public int deleteBook(String id) {
        bookDao.deleteById(id);
        return 1;
    }
}
