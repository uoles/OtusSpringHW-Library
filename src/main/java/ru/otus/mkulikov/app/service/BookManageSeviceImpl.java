package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Book;

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

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    @Override
    public Book getBookById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getFullBookById(int id) {
        Book book = bookDao.getById(id);
        return setBookInfo(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAllObjects();
    }

    @Override
    public List<Book> getFullBooks() {
        List<Book> books = bookDao.getAllObjects();
        for (Book book : books) {
            setBookInfo(book);
        }
        return books;
    }

    private Book setBookInfo(Book book) {
        book.setAuthor(authorDao.getById(book.getAuthorId()));
        book.setGenre(genreDao.getById(book.getGenreId()));
        return book;
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
}
