package ru.otus.mkulikov.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.mkulikov.AppTestConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 11:39
 */

@DisplayName("Класс Book")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestConfig.class)
class BookTest {

    @Test
    void getAddRecordDateString() {
        Book book = getNewBook();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertAll("book",
                  () -> assertNotNull(book),
                  () -> assertEquals(dateFormat.format(new Date()), book.getAddRecordDateString())
        );
    }

    private Book getNewBook() {
        return new Book(1, new Date(), "Test_Book", 2, 3, "Test_Comment");
    }
}