package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@Data
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_book")
    @SequenceGenerator(name = "sq_book", sequenceName = "sq_book", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "ADD_RECORD_DATE")
    private Date addRecordDate;
    @Column(name = "CAPTION")
    private String caption;
    @Column(name = "DESCRIPTION")
    private String description;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    private Author author;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
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
