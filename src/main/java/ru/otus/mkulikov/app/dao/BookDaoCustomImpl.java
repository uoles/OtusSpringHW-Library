package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.mkulikov.app.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public Book getById(long id) {
        List<Book> books = em.createQuery("select b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "where b.id = :id ", Book.class)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (books != null && !books.isEmpty()) ? books.get(0) : null;
    }

    @Override
    public List<Book> getAllObjects() {
        List<Book> books = em.createQuery("select b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "order by b.id ", Book.class)
                .getResultList();

        em.clear();
        return books;
    }
}

