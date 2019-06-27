package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 13:40
 */

@DataJpaTest
@DisplayName("Класс BookManageSevice")
@ComponentScan("ru.otus.mkulikov.app")
@TestPropertySource(locations= "classpath:application.yml")
class BookManageSeviceImplTest {

    @Autowired
    private BookManageSevice booksManageSevice;

    @Test
    @DisplayName("Получение книги по id")
    void getBookById() {
        Book book = booksManageSevice.getBookById(1L);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertNotNull(book.getAuthor()),
                () -> assertNotNull(book.getGenre()),
                () -> assertEquals(1L, book.getId()),
                () -> assertEquals("2019-01-01", DateUtil.dateToString(book.getAddRecordDate())),
                () -> assertEquals("book_1", book.getCaption()),
                () -> assertEquals(1, book.getAuthor().getId()),
                () -> assertEquals(1, book.getGenre().getId()),
                () -> assertEquals("description", book.getDescription())
        );
    }

    @Test
    @DisplayName("Получение всех книг")
    void getBooks() {
        List<Book> books = booksManageSevice.getBooks();

        assertAll(
                "books",
                () -> assertNotNull(books),
                () -> assertEquals(3, books.size()),
                () -> assertEquals("book_1", books.get(0).getCaption()),
                () -> assertEquals("book_2", books.get(1).getCaption()),
                () -> assertEquals("book_3", books.get(2).getCaption())
        );
    }

    @Test
    @DisplayName("Добавление книги")
    void addBook() {
        long id = booksManageSevice.addBook("Test_Book", 2, 3, "Test_Description");
        Book book = booksManageSevice.getBookById(id);

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertNotEquals(0, id),
                () -> assertEquals("Test_Book", book.getCaption()),
                () -> assertEquals(2, book.getAuthor().getId()),
                () -> assertEquals(3, book.getGenre().getId()),
                () -> assertEquals("Test_Description", book.getDescription())
        );
    }

    @Test
    @DisplayName("Обновление книги")
    void updateBook() {
        int count = booksManageSevice.updateBook(1L, "Test_Book", 1, 1, "Test_Description");
        Book book2 = booksManageSevice.getBookById(1L);

        assertAll(
                "book",
                () -> assertEquals(1, count),
                () -> assertEquals("Test_Book", book2.getCaption()),
                () -> assertEquals("Test_Description", book2.getDescription())
        );
    }

    @Test
    @DisplayName("Удаление книги по id")
    void deleteBook() {
        booksManageSevice.deleteBook(1L);
        assertThrows(NullPointerException.class, () -> { booksManageSevice.getBookById(1L); });
    }

}