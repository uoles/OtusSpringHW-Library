package ru.otus.mkulikov.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mkulikov.app.model.Book;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

public interface BookDao extends MongoRepository<Book, String> {

}
