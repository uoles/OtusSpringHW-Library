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
    public Author getAuthorById(long id) {
        Optional<Author> author = authorDao.findById(id);
        return author.get();
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
    public int addAuthor(String surname, String firstName, String secondName) {
        authorDao.save(new Author(surname, firstName, secondName));
        return 1;
    }

    @Override
    public int updateAuthor(long id, String surname, String firstName, String secondName) {
        Author author = authorDao.findById(id).get();
        author.setSurname(surname);
        author.setFirstName(firstName);
        author.setSecondName(secondName);

        authorDao.save(author);
        return 1;
    }

    @Override
    public int deleteAuthor(long id) {
        authorDao.deleteById(id);
        // при удалении записи, которая используется в другой таблице не выдает никакой ошибки,
        // ошибка выдается только при вызове следующей команды
        // поэтому вызываю count
        authorDao.count();
        return 1;
    }
}
