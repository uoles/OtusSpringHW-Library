package ru.otus.mkulikov.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@Data
public class Book {

    private final int id;
    private final Date add_record_date;
    private final String caption;
    private final int author_id;
    private final int genre_id;
    private final String comment;

    public Book(int id, Date add_record_date, String caption, int author_id, int genre_id, String comment) {
        this.id = id;
        this.add_record_date = add_record_date;
        this.caption = caption;
        this.author_id = author_id;
        this.genre_id = genre_id;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", add_record_date=" + add_record_date +
               ", caption='" + caption + '\'' +
               ", author_id=" + author_id +
               ", genre_id=" + genre_id +
               ", comment='" + comment + '\'' +
               "}\n";
    }
}
