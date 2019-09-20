package ru.otus.mkulikov.generators;

import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.otus.mkulikov.generators.GenerateAuthor.getAuthor;
import static ru.otus.mkulikov.generators.GenerateGenre.getGenre;

public class GenerateBook {

    private static final String ID_1 = "1";
    private static final String ID_2 = "2";
    private static final String ID_3 = "3";

    private static final String DATE_TIME = "2019-01-01 10:01:01";

    private static final String BOOK_NAME = "BookName";
    private static final String BOOK_DESCRIPTION = "Description";

    public static Book getBook(String id) {
        Author author = getAuthor(id);
        Genre genre = getGenre(id);
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        return new Book(id, date, BOOK_NAME, author, genre, BOOK_DESCRIPTION);
    }

    public static List<Book> getBooksList() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(ID_1));
        list.add(getBook(ID_2));
        list.add(getBook(ID_3));
        return list;
    }
}
