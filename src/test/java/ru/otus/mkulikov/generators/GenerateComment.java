package ru.otus.mkulikov.generators;

import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.otus.mkulikov.generators.GenerateBook.getBook;

public class GenerateComment {

    private static final String ID_1 = "1";
    private static final String ID_2 = "2";
    private static final String ID_3 = "3";
    private static final String ID_4 = "4";

    private static final String DATE_TIME = "2019-01-01 10:01:01";

    private static final String COMMENT_USER_NAME = "user";
    private static final String COMMENT_TEXT = "text";

    public static Comment getComment(String id, String bookId) {
        Date date = DateUtil.stringToDateTime(DATE_TIME);
        Book book = getBook(bookId);
        return new Comment(id, book, date, COMMENT_USER_NAME + id, COMMENT_TEXT + id);
    }

    public static List<Comment> getCommentList() {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(getComment(ID_1, ID_1));
        comments.add(getComment(ID_2, ID_1));
        comments.add(getComment(ID_3, ID_2));
        comments.add(getComment(ID_4, ID_2));
        return comments;
    }

    public static List<Comment> getCommentList(String bookId) {
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(getComment(ID_1, bookId));
        comments.add(getComment(ID_2, bookId));
        return comments;
    }
}
