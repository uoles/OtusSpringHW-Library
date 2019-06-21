package ru.otus.mkulikov.app.model;

import lombok.Data;

import javax.persistence.*;
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
    @Column(name = "BOOK_ID")
    private long bookId;
    @Column(name = "ADD_RECORD_DATE")
    private Date addRecordDate;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "TEXT")
    private String text;

    public Comment(long id, long bookId, Date addRecordDate, String userName, String text) {
        this.id = id;
        this.bookId = bookId;
        this.addRecordDate = addRecordDate;
        this.userName = userName;
        this.text = text;
    }

    public Comment(long bookId, Date addRecordDate, String userName, String text) {
        this.id = 0L;
        this.bookId = bookId;
        this.addRecordDate = addRecordDate;
        this.userName = userName;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", addRecordDate=" + addRecordDate +
                ", userName=" + userName +
                ", text='" + text + '\'' +
                "}\n";
    }
}
