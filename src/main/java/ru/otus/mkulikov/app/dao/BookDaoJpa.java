package ru.otus.mkulikov.app.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
@Repository
@Transactional
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao<Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        List<Book> books = em.createQuery("select distinct b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "left join b.comments c "
                                          + "where b.id = :id ", Book.class)
                .setParameter("id", id)
                .getResultList();

        em.clear();
        return (books != null) ? books.get(0) : null;
    }

    @Override
    public List<Book> getAllObjects() {
        List<Book> books = em.createQuery("select distinct b "
                                          + "from Book b "
                                          + "inner join fetch b.author a "
                                          + "inner join fetch b.genre g "
                                          + "left join b.comments c "
                                          + "order by b.id ", Book.class)
                .getResultList();

        em.clear();
        return books;
    }

    @Override
    public int save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        System.out.println("Book saved with id: " + book.getId());
        return 1;
    }

    @Override
    public int deleteObject(long id) {
        return em.createQuery("delete from Book b where b.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}

