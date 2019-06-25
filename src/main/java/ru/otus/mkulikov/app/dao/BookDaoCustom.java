package ru.otus.mkulikov.app.dao;

import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 24.06.2019
 * Time: 11:48
 */

@Repository
public interface BookDaoCustom<T extends Book> {

    Book getById(long id);

    List<Book> getAllObjects();
}
