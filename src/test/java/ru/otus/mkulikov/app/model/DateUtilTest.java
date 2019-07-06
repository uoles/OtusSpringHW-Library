package ru.otus.mkulikov.app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 29.05.2019
 * Time: 11:39
 */

@DisplayName("Класс DateUtil")
class DateUtilTest {

    private final String DATE_PATTERN = "yyyy-MM-dd";
    private final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Test
    @DisplayName("Перевод даты в строку, формат даты '" + DATE_PATTERN + "'")
    void dateToString() {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        assertEquals(dateFormat.format(date), DateUtil.dateToString(date));
    }

    @Test
    @DisplayName("Перевод даты в строку, формат даты '" + DATETIME_PATTERN + "'")
    void dateTimeToString() {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        assertEquals(dateFormat.format(date), DateUtil.dateTimeToString(date));
    }

    @Test
    @DisplayName("Перевод строки в дату, формат даты '" + DATETIME_PATTERN + "'")
    void stringToDateTime() {
        String datetime = "2019-01-01 10:01:01";

        Date date = DateUtil.stringToDateTime(datetime);
        DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);

        assertEquals(datetime, dateFormat.format(date));
    }
}