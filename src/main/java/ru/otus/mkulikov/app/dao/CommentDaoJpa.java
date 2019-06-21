package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;
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
        List<Comment> list = em.createQuery("select a from Comment a where a.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();

        // не использую getSingleResult, т.к. он возвращает ошибку, если нет данных
        // не хочу ошибку, хочу null
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public Comment getByBookId(long bookId) {
        List<Comment> list = em.createQuery("select a from Comment a where a.bookId = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();

        // не использую getSingleResult, т.к. он возвращает ошибку, если нет данных
        // не хочу ошибку, хочу null
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public List<Comment> getAllObjects() {
        return em.createQuery("select a from Comment a order by a.id", Comment.class)
                .getResultList();
    }

    @Override
    public int addObject(Comment comment) {
        em.persist(comment);
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Comment a where a.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int updateObject(Comment comment) {
        int count = em.createQuery("update Comment a set a.addRecordDate = :addRecordDate, a.userName = :userName , a.text = :text where a.id = :id ")
                .setParameter("addRecordDate", comment.getAddRecordDate())
                .setParameter("userName", comment.getUserName())
                .setParameter("text", comment.getText())
                .setParameter("id", comment.getId())
                .executeUpdate();

        em.clear();
        return count;
    }
}
