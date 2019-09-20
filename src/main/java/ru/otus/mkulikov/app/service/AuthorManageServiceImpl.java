package ru.otus.mkulikov.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mkulikov.app.dao.AuthorDao;
import ru.otus.mkulikov.app.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        Optional<Author> author = authorDao.findById(id);
        return author.orElse(null);
    }

    @Override
    public List<Author> getAuthors() {
        Iterable<Author> authors = authorDao.findAll();
        List<Author> list = StreamSupport
                .stream(authors.spliterator(), false)
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public Author addAuthor(String surname, String firstName, String secondName) {
        return authorDao.save(new Author(surname, firstName, secondName));
    }

    @Override
    public Author updateAuthor(String id, String surname, String firstName, String secondName) {
        Author author = authorDao.findById(id).orElse(null);
        author.setSurname(surname);
        author.setFirstName(firstName);
        author.setSecondName(secondName);

        return authorDao.save(author);
    }

    @Override
    public int deleteAuthor(String id) {
        authorDao.deleteById(id);
        return 1;
    }
}
