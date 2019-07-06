package ru.otus.mkulikov.app.dao;

import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:48
 */

@Repository
public interface CommentDaoCustom<T extends Comment> {

    Optional<T> getById(long id);

    List<T> getByBookId(long bookId);
}
