package ru.otus.mkulikov.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao extends MongoRepository<Book, String> {

    List<Book> findByAuthor_Id(String id);
}
