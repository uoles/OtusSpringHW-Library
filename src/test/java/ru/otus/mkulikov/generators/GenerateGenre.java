package ru.otus.mkulikov.generators;

import ru.otus.mkulikov.app.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenerateGenre {

    private static final String ID_1 = "1";
    private static final String ID_2 = "2";
    private static final String ID_3 = "3";

    private static final String GENRE_NAME = "GenreName";

    public static List<Genre> getGenreList() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(getGenre(ID_1));
        genres.add(getGenre(ID_2));
        genres.add(getGenre(ID_3));
        return genres;
    }

    public static Genre getGenre(String id) {
        return new Genre(id, GENRE_NAME + id);
    }
}
