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
    private final String first_name;
    private final String second_name;

    public Author(int id, String surname, String first_name, String second_name) {
        this.id = id;
        this.surname = surname;
        this.first_name = first_name;
        this.second_name = second_name;
    }

    @Override
    public String toString() {
        return "Author{" +
               "id=" + id +
               ", surname='" + surname + '\'' +
               ", first_name='" + first_name + '\'' +
               ", second_name='" + second_name + '\'' +
               "}\n";
    }
}
