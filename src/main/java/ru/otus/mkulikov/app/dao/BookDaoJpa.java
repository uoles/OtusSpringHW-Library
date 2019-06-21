package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 13:28
 */

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@Transactional
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao<Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        List<Book> books = em.createQuery("select b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "inner join fetch b.comment c "
                                          + "where b.id = :id ", Book.class)
                .setParameter("id", id)
                .getResultList();

        return (books != null) ? books.get(0) : null;
    }

    @Override
    public List<Book> getAllObjects() {
        List<Book> books = em.createQuery("select b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "inner join fetch b.comment c "
                                          + "order by b.id ", Book.class)
                .getResultList();

        return books;
    }

    @Override
    public int addObject(Book book) {
        em.persist(book);
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Book b where b.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int updateObject(Book book) {
        int count = em.createNativeQuery(
                "update Book b "
                + "set b.add_record_date = :add_record_date, b.caption = :caption, b.author_id = :author_id, b.genre_id = :genre_id, b.description = :description "
                + "where b.id = :id ")
                .setParameter("add_record_date", new Date())
                .setParameter("caption", book.getCaption())
                .setParameter("author_id", book.getAuthor().getId())
                .setParameter("genre_id", book.getGenre().getId())
                .setParameter("description", book.getDescription())
                .setParameter("id", book.getId())
                .executeUpdate();

        em.clear();
        return count;
    }
}

