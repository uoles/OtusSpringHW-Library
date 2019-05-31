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
 */

@Data
public class Book {

    private long id;
    private Date addRecordDate;
    private String caption;
    private Author author;
    private Genre genre;
    private String comment;

    public Book(long id, Date addRecordDate, String caption, Author author, Genre genre, String comment) {
        this.id = id;
        this.addRecordDate = addRecordDate;
        this.author = author;
        this.genre = genre;
        this.caption = caption;
        this.comment = comment;
    }

    public Book(long id, String caption, Author author, Genre genre, String comment) {
        this.id = id;
        this.caption = caption;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    public Book(String caption, Author author, Genre genre, String comment) {
        this.caption = caption;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    public String getAddRecordDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return addRecordDate != null ? dateFormat.format(addRecordDate) : null;
    }

    public String getAuthorString() {
        return (author != null)
                ? ", author='" + author.toFormatedString() + '\''
                : "";
    }

    public String getGenreString() {
        return (genre != null)
                ? ", genre='" + genre.getName() + '\''
                : "";
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", addRecordDate=" + addRecordDate +
               ", caption='" + caption + '\'' +
               ", comment='" + comment + '\'' +
               getAuthorString() +
               getGenreString() +
               "}\n";
    }
}
