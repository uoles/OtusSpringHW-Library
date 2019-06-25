package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.mkulikov.app.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
public class CommentDaoCustomImpl implements CommentDaoCustom<Comment> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(long id) {
        List<Comment> list = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "inner join fetch c.book b " +
                        "where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        List<Comment> list = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "inner join fetch c.book b " +
                        "where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();

        em.clear();
        return list;
    }
}
