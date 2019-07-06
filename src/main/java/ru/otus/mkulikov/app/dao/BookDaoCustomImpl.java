package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.mkulikov.app.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
public class BookDaoCustomImpl implements BookDaoCustom<Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> getById(long id) {
        EntityGraph entityGraph = em.getEntityGraph("BookGraph");
        List<Book> books = em.createQuery("select b from Book b where b.id = :id ", Book.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (books != null && !books.isEmpty()) ? Optional.of(books.get(0)) : Optional.empty();
    }

    @Override
    public List<Book> getAllObjects() {
        EntityGraph entityGraph = em.getEntityGraph("BookGraph");
        List<Book> books = em.createQuery("select b from Book b order by b.id ", Book.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();

        em.clear();
        return books;
    }
}

