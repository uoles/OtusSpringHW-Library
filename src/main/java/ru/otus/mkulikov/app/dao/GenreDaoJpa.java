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
        List<Genre> list = em.createQuery(
                    "select g " +
                       "from Genre g " +
                       "where g.id = :id ", Genre.class)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public List<Genre> getAllObjects() {
        List<Genre> list = em.createQuery(
                     "select g " +
                        "from Genre g " +
                        "order by g.id", Genre.class)
                .getResultList();

        em.clear();
        return list;
    }

    @Override
    public int save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        System.out.println("Genre saved with id: " + genre.getId());
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Genre g where g.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}
