package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:07
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@Transactional
@RequiredArgsConstructor
public class GenreDaoJpa implements GenreDao<Genre> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre getById(long id) {
        List<Genre> list = em.createQuery("select g from Genre g where g.id = :id ", Genre.class)
                .setParameter("id", id)
                .getResultList();

        // не использую getSingleResult, т.к. он возвращает ошибку, если нет данных
        // не хочу ошибку, хочу null
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public List<Genre> getAllObjects() {
        return em.createQuery("select g from Genre g order by g.id", Genre.class)
                .getResultList();
    }

    @Override
    public int addObject(Genre genre) {
        em.persist(genre);
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Genre g where g.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int updateObject(Genre genre) {
        return em.createQuery("update Genre g set g.name = :name where g.id = :id ")
                .setParameter("name", genre.getName())
                .setParameter("id", genre.getId())
                .executeUpdate();
    }
}
