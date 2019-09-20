package ru.otus.mkulikov.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:02
 */

@Data
@Document
public class Author {

    @Id
    private String id;
    @Field(value = "surname")
    private String surname;
    @Field(value = "firstName")
    private String firstName;
    @Field(value = "secondName")
    private String secondName;

    public Author() {
    }

    public Author(String id, String surname, String firstName, String secondName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Author(String surname, String firstName, String secondName) {
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                "}\n";
    }
}
