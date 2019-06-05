package ru.otus.mkulikov.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 05.06.2019
 * Time: 11:33
 */

public class DateUtil {

    private final static String DATE_FORMAT = "yyyy-MM-dd";

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return date != null ? dateFormat.format(date) : null;
    }
}
