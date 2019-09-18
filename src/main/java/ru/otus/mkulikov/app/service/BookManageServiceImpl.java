package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.exception.ObjectNotFound;
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
public class BookManageServiceImpl implements BookManageService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book getBookById(String id) {
        return bookDao.findById(id).orElseThrow(() -> new ObjectNotFound("Book", id));
    }

    @Override
    public List<Book> getBookByAuthorId(String id) {
        return bookDao.findByAuthor_Id(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book addBook(String caption, String authorId, String genreId, String description) {
        Author author = authorDao.findById(authorId).orElseThrow(() -> new ObjectNotFound("Author", authorId));
        Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ObjectNotFound("Genre", genreId));

        return bookDao.save(new Book(new Date(), caption, author, genre, description));
    }

    @Override
    public Book updateBook(String id, String caption, String authorId, String genreId, String description) {
        Author author = authorDao.findById(authorId).orElseThrow(() -> new ObjectNotFound("Author", authorId));
        Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ObjectNotFound("Genre", genreId));

        Book book = bookDao.findById(id).orElseThrow(() -> new ObjectNotFound("Book", id));
        book.setCaption(caption);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setDescription(description);

        return bookDao.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book orig = bookDao.findById(book.getId()).orElseThrow(() -> new ObjectNotFound("Book", book.getId()));
        orig.setCaption(book.getCaption());
        orig.setDescription(book.getDescription());

        return bookDao.save(orig);
    }

    @Override
    public int deleteBook(String id) {
        bookDao.deleteById(id);
        return 1;
    }
}
