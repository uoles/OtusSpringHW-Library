package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.mkulikov.app.model.Comment;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
    public Optional<Comment> getById(long id) {
        EntityGraph entityGraph = em.getEntityGraph("CommentGraph");
        List<Comment> list = em.createQuery("select c from Comment c where c.id = :id ", Comment.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (list != null && !list.isEmpty()) ? Optional.of(list.get(0)) : Optional.empty();
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        EntityGraph entityGraph = em.getEntityGraph("CommentGraph");
        List<Comment> list = em.createQuery("select c from Comment c where c.book.id = :bookId ", Comment.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setParameter("bookId", bookId)
                .getResultList();

        em.clear();
        return list;
    }
}
