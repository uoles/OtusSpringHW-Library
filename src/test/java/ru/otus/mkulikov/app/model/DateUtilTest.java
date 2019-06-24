package ru.otus.mkulikov.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 11:39
 */

@DisplayName("Класс DateUtil")
@RunWith(SpringRunner.class)
class DateUtilTest {

    @Test
    @DisplayName("Формат даты 'yyyy-MM-dd'")
    void dateToString() {
        Book book = getNewBook();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(dateFormat.format(new Date()), DateUtil.dateToString(book.getAddRecordDate()))
        );
    }

    @Test
    @DisplayName("Формат даты 'yyyy-MM-dd HH:mm:ss'")
    void dateTimeToString() {
        Book book = getNewBook();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        assertAll(
                "book",
                () -> assertNotNull(book),
                () -> assertEquals(dateFormat.format(new Date()), DateUtil.dateTimeToString(book.getAddRecordDate()))
        );
    }

    private Book getNewBook() {
        Author author = new Author(1L, "TestSurname", "TestFirstName", "TestSecondName");
        Genre genre = new Genre("Test4");
        return new Book(1L, new Date(), "Test_Book", author, genre, "Test_Comment");
    }
}