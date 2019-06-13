package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;

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
public class AuthorDaoJdbc implements AuthorDao<Author> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getById(long id) {
        List<Author> list = em.createQuery("select a from Author a where a.id = :id", Author.class)
                .setParameter("id", id)
                .getResultList();

        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public List<Author> getAllObjects() {
        return em.createQuery("select a from Author a order by a.id", Author.class)
                .getResultList();
    }

    @Override
    public int addObject(Author author) {
        em.persist(author);
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Author a where a.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int updateObject(Author author) {
        return em.createQuery("update Author a set a.surname = :surname, a.first_name = :firstName, a.second_name = :secondName where a.id = :id ")
                .setParameter("surname", author.getSurname())
                .setParameter("firstName", author.getFirstName())
                .setParameter("secondName", author.getSecondName())
                .setParameter("id", author.getId())
                .executeUpdate();
    }
}
