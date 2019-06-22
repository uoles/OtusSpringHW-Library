package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Book;
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
@Repository
@Transactional
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao<Comment> {

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
    public List<Comment> getByBook(Book book) {
        List<Comment> list = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "inner join fetch c.book b " +
                        "where c.book = :book", Comment.class)
                .setParameter("book", book)
                .getResultList();

        em.clear();
        return list;
    }

    @Override
    public int save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        System.out.println("Comment saved with id: " + comment.getId());
        return 1;
    }

    @Override
    public List<Comment> getAllObjects() {
        return em.createQuery("select a from Comment a order by a.id", Comment.class)
                .getResultList();
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Comment a where a.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}
