package ru.otus.mkulikov.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mkulikov.app.model.Book;
import ru.otus.mkulikov.app.model.Comment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:48
 */

public interface CommentDao extends MongoRepository<Comment, String> {

//    @Query(value = "{ 'book.id' : ?0 }")
//    List<Comment> findByBook(long id);

    List<Comment> findByBook(Book book);

    void deleteCommentsByBook(Book book);
}
