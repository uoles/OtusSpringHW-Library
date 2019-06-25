package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:38
 */

@Data
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_comment")
    @SequenceGenerator(name = "sq_comment", sequenceName = "sq_comment", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "ADD_RECORD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addRecordDate;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "TEXT")
    private String text;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    private Book book;

    public Comment() {
    }

    public Comment(long id, Book book, Date addRecordDate, String userName, String text) {
        this.id = id;
        this.book = book;
        this.addRecordDate = addRecordDate;
        this.userName = userName;
        this.text = text;
    }

    public Comment(Book book, Date addRecordDate, String userName, String text) {
        this.id = 0L;
        this.book = book;
        this.addRecordDate = addRecordDate;
        this.userName = userName;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", addRecordDate=" + addRecordDate +
                ", userName=" + userName +
                ", text='" + text + '\'' +
                "}\n";
    }
}
