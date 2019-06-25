package ru.otus.mkulikov.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.mkulikov.app.model.Comment;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 20.06.2019
 * Time: 23:48
 */

@Repository
public interface CommentDao extends JpaRepository<Comment, Long>, CommentDaoCustom<Comment> {

}
