package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

@NamedEntityGraph(
        name = "BookGraph",
        attributeNodes = {
                @NamedAttributeNode(value = "id"),
                @NamedAttributeNode(value = "addRecordDate"),
                @NamedAttributeNode(value = "caption"),
                @NamedAttributeNode(value = "description"),
                @NamedAttributeNode(value = "author", subgraph = "authorGraph"),
                @NamedAttributeNode(value = "genre", subgraph = "genreGraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "authorGraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "id"),
                                @NamedAttributeNode(value = "surname"),
                                @NamedAttributeNode(value = "firstName"),
                                @NamedAttributeNode(value = "secondName")
                        }
                ),
                @NamedSubgraph(
                        name = "genreGraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "id"),
                                @NamedAttributeNode(value = "name")
                        }
                )
        }
)

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
