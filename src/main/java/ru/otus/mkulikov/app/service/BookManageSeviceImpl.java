package ru.otus.mkulikov.app.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.CommentDao;
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
    private final CommentDao commentDao;

    @Override
    public Book getBookById(String id) {
        try {
            return bookDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book"));
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book addBook(String caption, String authorId, String genreId, String description) {
        try {
            Author author = authorDao.findById(authorId).orElseThrow(() -> new ObjectNotFoundException("Author"));
            Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ObjectNotFoundException("Genre"));

            return bookDao.save(new Book(new Date(), caption, author, genre, description));
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Book updateBook(String id, String caption, String authorId, String genreId, String description) {
        try {
            Author author = authorDao.findById(authorId).orElseThrow(() -> new ObjectNotFoundException("Author"));
            Genre genre = genreDao.findById(genreId).orElseThrow(() -> new ObjectNotFoundException("Genre"));

            Book book = bookDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book"));
            book.setCaption(caption);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setGenre(genre);
            book.setDescription(description);

            return bookDao.save(book);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteBook(String id) {
        try {
            Book book = bookDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book"));
            commentDao.deleteCommentsByBook(book);
            bookDao.delete(book);
            return 1;
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
