package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:38
 */

@Data
@Document
public class Comment {

    @Id
    private String id;
    @Field(value = "addRecordDate")
    private Date addRecordDate;
    @Field(value = "userName")
    private String userName;
    @Field(value = "text")
    private String text;

    @DBRef
    private Book book;

    public Comment() {
    }

    public Comment(String id, Book book, Date addRecordDate, String userName, String text) {
        this.id = id;
        this.book = book;
        this.addRecordDate = addRecordDate;
        this.userName = userName;
        this.text = text;
    }

    public Comment(Book book, Date addRecordDate, String userName, String text) {
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
                ", book='" + book + '\'' +
                "}\n";
    }
}
