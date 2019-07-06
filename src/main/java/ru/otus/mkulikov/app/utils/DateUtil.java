package ru.otus.mkulikov.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 05.06.2019
 * Time: 11:33
 */

public class DateUtil {

    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return date != null ? dateFormat.format(date) : null;
    }

    public static String dateTimeToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return date != null ? dateFormat.format(date) : null;
    }

    public static java.util.Date stringToDateTime(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(stringDate, formatter);
        return dateTime != null ? convertToDateViaInstant(dateTime) : null;
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
