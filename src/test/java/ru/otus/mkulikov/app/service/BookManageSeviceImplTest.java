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

    private final long ID_1 = 1L;
    private final long ID_2 = 2L;
    private final long ID_3 = 3L;

    private final int OBJECT_COUNT_3 = 3;

    private final String GENRE_NAME = "Genre";

    private final String AUTHOR_SURNAME = "Surname";
    private final String AUTHOR_FIRST_NAME = "FirstName";
    private final String AUTHOR_SECOND_NAME = "SecondName";

    private final String BOOK_NAME = "BookName";
    private final String BOOK_DESCRIPTION = "Description";

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
        Book book = getBook(ID_1);
        when(bookDao.findById(anyLong())).thenReturn( Optional.of(book) );
        Book bookById = booksManageSevice.getBookById(ID_1);

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
        assertThat(comments).hasSize(OBJECT_COUNT_3);
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
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(ID_1)) );
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(ID_1)) );

        long id = booksManageSevice.addBook(BOOK_NAME, ID_1, ID_1, BOOK_DESCRIPTION);

        assertThat(id).isEqualTo(ID_2);
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
        when(authorDao.findById(anyLong())).thenReturn( Optional.of(getAuthor(ID_1)) );
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(ID_1)) );
        when(bookDao.findById(anyLong())).thenReturn( Optional.of(getBook(ID_1)) );

        int count = booksManageSevice.updateBook(ID_1, BOOK_NAME, ID_1, ID_1, BOOK_DESCRIPTION);

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteBook() {
        doThrow(DataIntegrityViolationException.class).when(bookDao).deleteById(ID_1);
        assertThrows(DataIntegrityViolationException.class, () -> { booksManageSevice.deleteBook(ID_1); });
    }

    private Author getAuthor(long id) {
        return new Author(id, AUTHOR_SURNAME + id, AUTHOR_FIRST_NAME + id, AUTHOR_SECOND_NAME + id);
    }

    private Genre getGenre(long id) {
        return new Genre(id, GENRE_NAME + id);
    }

    private Book getBook(long id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        return new Book(id, BOOK_NAME, author, genre, BOOK_DESCRIPTION);
    }

    private List<Book> getBooksList() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(ID_1));
        list.add(getBook(ID_2));
        list.add(getBook(ID_3));
        return list;
    }
}