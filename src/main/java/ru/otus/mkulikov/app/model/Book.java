package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@Data
@Document
//@KeySpace("book")
public class Book {

    @Id
    private long id;
    private Date addRecordDate;
    private String caption;
    private String description;

    @DBRef
    private Author author;
    @DBRef
    private Genre genre;

    public Book() {
    }

    public Book(long id, Date addRecordDate, String caption, Author author, Genre genre, String description) {
        this.id = id;
        this.addRecordDate = addRecordDate;
        this.author = author;
        this.genre = genre;
        this.caption = caption;
        this.description = description;
    }

    public Book(long id, String caption, Author author, Genre genre, String description) {
        this.id = id;
        this.addRecordDate = new Date();
        this.caption = caption;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public Book(String caption, Author author, Genre genre, String description) {
        this.id = 0L;
        this.addRecordDate = new Date();
        this.caption = caption;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", addRecordDate=" + addRecordDate +
                ", caption='" + caption + '\'' +
                ", description=" + description +
                ", author=" + author +
                ", genre=" + genre +
                "}\n";
    }
}
