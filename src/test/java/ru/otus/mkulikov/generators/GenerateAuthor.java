package ru.otus.mkulikov.generators;

import ru.otus.mkulikov.app.model.Author;

import java.util.ArrayList;
import java.util.List;

public class GenerateAuthor {

    private static final String ID_1 = "1";
    private static final String ID_2 = "2";
    private static final String ID_3 = "3";

    private static final String SURNAME = "Surname";
    private static final String FIRST_NAME = "FirstName";
    private static final String SECOND_NAME = "SecondName";

    public static List<Author> getAuthorList() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(getAuthor(ID_1));
        authors.add(getAuthor(ID_2));
        authors.add(getAuthor(ID_3));
        return authors;
    }

    public static Author getAuthor(String id) {
        return new Author(id, SURNAME + id, FIRST_NAME + id, SECOND_NAME + id);
    }
}
