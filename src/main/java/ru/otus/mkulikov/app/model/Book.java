package ru.otus.mkulikov.app.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

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
    @Column(name = "COMMENT")
    private String comment;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Genre genre;

    public Book() {
    }

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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", addRecordDate=" + addRecordDate +
                ", caption='" + caption + '\'' +
                ", comment='" + comment + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                "}\n";
    }
}
