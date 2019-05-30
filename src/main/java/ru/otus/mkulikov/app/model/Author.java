package ru.otus.mkulikov.app.model;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:02
 */

@Data
public class Author {

    private final int id;
    private final String surname;
    private final String firstName;
    private final String secondName;

    public Author(int id, String surname, String firstName, String secondName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Author(String surname, String firstName, String secondName) {
        this.id = 0;
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
