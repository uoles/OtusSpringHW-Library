package ru.otus.mkulikov.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.mkulikov.app.model.Author;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.utils.DateUtil;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 19.08.2019
 * Time: 23:33
 */

@ChangeLog(order = "001")
public class DbChangeLog {

    private final String DATE_TIME = "2019-01-01 10:01:01";
    private Date date = DateUtil.stringToDateTime(DATE_TIME);

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;

    private Author author1;
    private Author author2;
    private Author author3;

    private Book book1;
    private Book book2;
    private Book book3;

    @ChangeSet(order = "000", id = "dropDB", author = "kulikov-mv", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "kulikov-mv", runAlways = true)
    public void initGenres(MongoTemplate template) {
        genre1 = template.save(new Genre("GenreName1"));
        genre2 = template.save(new Genre("GenreName2"));
        genre3 = template.save(new Genre("GenreName3"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "kulikov-mv", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        author1 = template.save(new Author("Surname1", "FirstName1", "SecondName1"));
        author2 = template.save(new Author("Surname2", "FirstName2", "SecondName2"));
        author3 = template.save(new Author("Surname3", "FirstName3", "SecondName3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "kulikov-mv", runAlways = true)
    public void initBooks(MongoTemplate template) {
        book1 = template.save(new Book(date, "BookName1", author1, genre1, "Description1"));
        book2 = template.save(new Book(date, "BookName2", author2, genre2, "Description2"));
        book3 = template.save(new Book(date, "BookName3", author3, genre3, "Description3"));
    }

    @ChangeSet(order = "004", id = "initComments", author = "kulikov-mv", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(new Comment(book1, date, "user1", "text1"));
        template.save(new Comment(book1, date, "user2", "text2"));
        template.save(new Comment(book2, date, "user3", "text3"));
        template.save(new Comment(book2, date, "user4", "text4"));
    }
}
