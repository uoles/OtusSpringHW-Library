package ru.otus.mkulikov.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mkulikov.app.model.Genre;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

public interface GenreDao extends MongoRepository<Genre, String> {

}
