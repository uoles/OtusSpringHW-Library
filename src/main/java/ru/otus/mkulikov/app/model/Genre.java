package ru.otus.mkulikov.app.model;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:04
 *
 * Сущность таблицы жанров
 */

@Data
public class Genre {

    private final int id;
    private final String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.id = 0;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
               "id=" + id +
               ", name='" + name + '\'' +
               "}\n";
    }
}