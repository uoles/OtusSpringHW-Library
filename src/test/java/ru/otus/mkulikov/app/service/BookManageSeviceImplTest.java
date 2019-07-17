package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.dao.BookDao;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 13:40
 */

@DisplayName("Класс BookManageSevice")
@ExtendWith(MockitoExtension.class)
class BookManageSeviceImplTest {

    @Mock
    private BookDao bookDao;

    @Mock
    private AuthorDao authorDao;

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private BookManageSeviceImpl booksManageSevice;

    @Test
    @DisplayName("Получение книги по id")
    void getBookById() {
        Book book = getBook(1L);
        when(bookDao.findById(anyLong())).thenReturn( Optional.of(book) );
        Book bookById = booksManageSevice.getBookById(1L);

        assertThat(bookById).isNotNull();
        assertThat(bookById).isEqualTo(book);
    }

    @Test
    @DisplayName("Получение всех книг")
    void getBooks() {
        List<Book> list = getBooksList();
        when(bookDao.findAll()).thenReturn( list );
        List<Book> comments = booksManageSevice.getBooks();

        assertThat(comments).isNotNull();
        assertThat(comments).hasSize(3);
        assertThat(comments).containsAll(list);
    }

    @Test
    @DisplayName("Добавление книги")
    void addBook() {
        when(bookDao.save(any(Book.class))).then(new Answer<Book>() {
            int sequence = 1;

            @Override
            public Book answer(InvocationOnMock invocationOnMock) throws Throwable {
                Book book = (Book) invocationOnMock.getArgument(0);
                book.setId(++sequence);
                return book;
            }
        });
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(1L)) );
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(1L)) );

        long id = booksManageSevice.addBook("Caption", 1L, 1L, "description");

        assertThat(id).isEqualTo(2L);
    }

    @Test
    @DisplayName("Обновление книги")
    void updateBook() {
        when(bookDao.save(any(Book.class))).then(new Answer<Book>() {

            @Override
            public Book answer(InvocationOnMock invocationOnMock) throws Throwable {
                Book book = (Book) invocationOnMock.getArgument(0);
                book.setId(1L);
                return book;
            }
        });
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(1L)) );
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(1L)) );
        when(bookDao.findById(anyLong())).thenReturn( Optional.of(getBook(1L)) );

        int count = booksManageSevice.updateBook(1L, "Caption", 1L, 1L, "description");

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteBook() {
        doThrow(DataIntegrityViolationException.class).when(bookDao).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> { booksManageSevice.deleteBook(1L); });
    }

    private Author getAuthor(long id) {
        return new Author(id, "Surname" + id, "FirstName" + id, "SecondName" + id);
    }

    private Genre getGenre(long id) {
        return new Genre(id, "Genre" + id);
    }

    private Book getBook(long id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        return new Book(id, "Test_Book", author, genre, "Test_Description");
    }

    private List<Book> getBooksList() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(1L));
        list.add(getBook(2L));
        list.add(getBook(3L));
        return list;
    }
}