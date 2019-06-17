package ru.otus.mkulikov.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:02
 */

@Data
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_author")
    @SequenceGenerator(name = "sq_author", sequenceName = "sq_author", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "SECOND_NAME")
    private String secondName;

    public Author() {
    }

    public Author(long id, String surname, String firstName, String secondName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Author(String surname, String firstName, String secondName) {
        this.id = 0L;
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String toFormatedString() {
        return getSurname() + " " + getFirstName() + " " + getSecondName();
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
