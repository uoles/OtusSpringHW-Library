package ru.otus.mkulikov.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mkulikov.app.model.Author;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 23.05.2019
 * Time: 17:06
 */

@Repository
@Transactional
public interface AuthorDao extends JpaRepository<Author, Long> {

}
