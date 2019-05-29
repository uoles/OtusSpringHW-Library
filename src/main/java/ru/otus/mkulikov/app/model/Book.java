package ru.otus.mkulikov.app.model;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 *
 * Сущность таблицы книг
 */

@Data
public class Book {

    private int id;
    private Date addRecordDate;
    private String caption;
    private int authorId;
    private int genreId;
    private String comment;

    public Book(int id, Date addRecordDate, String caption, int authorId, int genreId, String comment) {
        this.id = id;
        this.addRecordDate = addRecordDate;
        this.caption = caption;
        this.authorId = authorId;
        this.genreId = genreId;
        this.comment = comment;
    }

    public Book(int id, String caption, int authorId, int genreId, String comment) {
        this.id = id;
        this.caption = caption;
        this.authorId = authorId;
        this.genreId = genreId;
        this.comment = comment;
    }

    public Book(String caption, int authorId, int genreId, String comment) {
        this.caption = caption;
        this.authorId = authorId;
        this.genreId = genreId;
        this.comment = comment;
    }

    public String getAddRecordDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return addRecordDate != null ? dateFormat.format(addRecordDate) : null;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", addRecordDate=" + addRecordDate +
               ", caption='" + caption + '\'' +
               ", authorId=" + authorId +
               ", genreId=" + genreId +
               ", comment='" + comment + '\'' +
               "}\n";
    }
}
