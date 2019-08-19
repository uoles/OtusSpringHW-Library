package ru.otus.mkulikov.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mkulikov.app.model.Author;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

public interface AuthorDao extends MongoRepository<Author, String> {

}
