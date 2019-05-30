package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:53
 */

@Service
@RequiredArgsConstructor
public class AuthorManageServiceImpl implements AuthorManageService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorById(int id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.getAllObjects();
    }

    @Override
    public int addAuthor(String surname, String firstName, String secondName) {
        return authorDao.addObject(new Author(surname, firstName, secondName));
    }

    @Override
    public int updateAuthor(int id, String surname, String firstName, String secondName) {
        return authorDao.updateObject(new Author(id, surname, firstName, secondName));
    }

    @Override
    public int deleteAuthor(int id) {
        return authorDao.deleteObject(id);
    }
}
