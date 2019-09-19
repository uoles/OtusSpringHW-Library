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
import ru.otus.mkulikov.app.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateBook.getBook;
import static ru.otus.mkulikov.generators.GenerateBook.getBooksList;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenre;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 13:40
 */

@DisplayName("Класс BookManageSevice")
@ExtendWith(MockitoExtension.class)
class BookManageSeviceImplTest {

    private final String ID_1 = "1";
    private final String ID_2 = "2";

    private final int OBJECT_COUNT_1 = 1;
    private final int OBJECT_COUNT_3 = 3;

    private final String BOOK_NAME = "BookName";
    private final String BOOK_DESCRIPTION = "Description";

    @Mock
    private BookDao bookDao;

    @Mock
    private AuthorDao authorDao;

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private BookManageServiceImpl booksManageSevice;

    @Test
    @DisplayName("Получение книги по id")
    void getBookById() {
        Book book = getBook(ID_1);
        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
        Book bookById = booksManageSevice.getBookById(ID_1);

        assertThat(bookById).isNotNull();
        assertThat(bookById).isEqualTo(book);
    }

    @Test
    @DisplayName("Получение всех книг")
    void getBooks() {
        List<Book> list = getBooksList();
        when(bookDao.findAll()).thenReturn(list);
        List<Book> books = booksManageSevice.getBooks();

        assertThat(books).isNotNull();
        assertThat(books).hasSize(OBJECT_COUNT_3);
        assertThat(books).containsAll(list);
    }

    @Test
    @DisplayName("Получение книг по id автора")
    void getBooksByAuthorId() {
        Book book = getBook(ID_1);

        List<Book> list = new ArrayList<>();
        list.add(book);

        when(bookDao.findByAuthor_Id(anyString())).thenReturn(list);
        List<Book> books = booksManageSevice.getBookByAuthorId(ID_1);

        assertThat(books).isNotNull();
        assertThat(books).hasSize(OBJECT_COUNT_1);
        assertThat(books).contains(book);
    }

    @Test
    @DisplayName("Получение книг по id жанра")
    void getBooksByGenreId() {
        Book book = getBook(ID_1);

        List<Book> list = new ArrayList<>();
        list.add(book);

        when(bookDao.findByGenre_Id(anyString())).thenReturn(list);
        List<Book> books = booksManageSevice.getBookByGenreId(ID_1);

        assertThat(books).isNotNull();
        assertThat(books).hasSize(OBJECT_COUNT_1);
        assertThat(books).contains(book);
    }

    @Test
    @DisplayName("Добавление книги")
    void addBook() {
        when(bookDao.save(any(Book.class))).then(new Answer<Book>() {
            @Override
            public Book answer(InvocationOnMock invocationOnMock) throws Throwable {
                Book book = (Book) invocationOnMock.getArgument(0);
                book.setId("2");
                return book;
            }
        });
        when(authorDao.findById(anyString())).thenReturn(Optional.of(getAuthor(ID_1)));
        when(genreDao.findById(anyString())).thenReturn(Optional.of(getGenre(ID_1)));

        Book book = booksManageSevice.addBook(BOOK_NAME, ID_1, ID_1, BOOK_DESCRIPTION);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(ID_2);
    }

    @Test
    @DisplayName("Обновление книги")
    void updateBook() {
        when(bookDao.save(any(Book.class))).then(new Answer<Book>() {

            @Override
            public Book answer(InvocationOnMock invocationOnMock) throws Throwable {
                Book book = (Book) invocationOnMock.getArgument(0);
                book.setId(ID_1);
                return book;
            }
        });
        when(authorDao.findById(anyString())).thenReturn(Optional.of(getAuthor(ID_1)));
        when(genreDao.findById(anyString())).thenReturn(Optional.of(getGenre(ID_1)));
        when(bookDao.findById(anyString())).thenReturn(Optional.of(getBook(ID_1)));

        Book book = booksManageSevice.updateBook(ID_1, BOOK_NAME, ID_1, ID_1, BOOK_DESCRIPTION);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(ID_1);
    }

    @Test
    @DisplayName("Обновление книги объектом")
    void updateBookByObject() {
        when(bookDao.save(any(Book.class))).then(new Answer<Book>() {

            @Override
            public Book answer(InvocationOnMock invocationOnMock) throws Throwable {
                Book book = (Book) invocationOnMock.getArgument(0);
                book.setId(ID_1);
                return book;
            }
        });
        Book book = booksManageSevice.updateBook(getBook(ID_1));

        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(ID_1);
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteBook() {
        doThrow(DataIntegrityViolationException.class).when(bookDao).deleteById(ID_1);
        assertThrows(DataIntegrityViolationException.class, () -> {
            booksManageSevice.deleteBook(ID_1);
        });
    }
}