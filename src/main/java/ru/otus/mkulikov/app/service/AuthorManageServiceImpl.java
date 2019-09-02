package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.exception.ObjectNotFound;
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
    public Author getAuthorById(String id) {
        return authorDao.findById(id).orElseThrow(() -> new ObjectNotFound("Author", id));
    }

    @Override
    public List<Author> getAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Author addAuthor(String surname, String firstName, String secondName) {
        return authorDao.save(new Author(surname, firstName, secondName));
    }

    @Override
    public Author updateAuthor(String id, String surname, String firstName, String secondName) {
        Author author = authorDao.findById(id).orElseThrow(() -> new ObjectNotFound("Author", id));
        author.setSurname(surname);
        author.setFirstName(firstName);
        author.setSecondName(secondName);

        return authorDao.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        Author orig = authorDao.findById(author.getId()).orElseThrow(() -> new ObjectNotFound("Author", author.getId()));
        orig.setSurname(author.getSurname());
        orig.setFirstName(author.getFirstName());
        orig.setSecondName(author.getSecondName());

        return authorDao.save(orig);
    }

    @Override
    public int deleteAuthor(String id) {
        authorDao.deleteById(id);
        return 1;
    }
}
