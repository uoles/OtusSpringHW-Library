package ru.otus.mkulikov.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.mkulikov.app.dao.GenreDao;
import ru.otus.mkulikov.app.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 15:56
 */

@DisplayName("Класс GenreManageSevice")
@ExtendWith(MockitoExtension.class)
class GenreManageSeviceImplTest {

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private GenreManageServiceImpl genreManageService;

    @Test
    @DisplayName("Получение жанра по id")
    void getGenreById() {
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(1L)) );
        Genre genre = genreManageService.getGenreById(1L);

        assertThat(genre).isNotNull();
        assertThat(genre).isEqualTo(getGenre(1L));
    }

    @Test
    @DisplayName("Получение всех жанров")
    void getGenres() {
        when(genreDao.findAll()).thenReturn( getGenreList() );
        List<Genre> authors = genreManageService.getGenres();

        assertThat(authors).isNotNull();
        assertThat(authors).hasSize(3);
        assertThat(authors).containsAll(getGenreList());
    }

    @Test
    @DisplayName("Добавление жанра")
    void addGenre() {
        when(genreDao.save(any(Genre.class))).then(new Answer<Genre>() {
            int sequence = 1;

            @Override
            public Genre answer(InvocationOnMock invocationOnMock) throws Throwable {
                Genre genre = (Genre) invocationOnMock.getArgument(0);
                genre.setId(++sequence);
                return genre;
            }
        });

        long id = genreManageService.addGenre("Test4");

        assertThat(id).isEqualTo(2L);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateGenre() {
        when(genreDao.save(any(Genre.class))).then(new Answer<Genre>() {

            @Override
            public Genre answer(InvocationOnMock invocationOnMock) throws Throwable {
                Genre genre = (Genre) invocationOnMock.getArgument(0);
                genre.setId(1L);
                return genre;
            }
        });
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(1L)) );

        int count = genreManageService.updateGenre(1L, "UpdatedName");

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление жанра, который используется в таблице книг")
    void deleteGenre() {
        doThrow(DataIntegrityViolationException.class).when(genreDao).deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> { genreManageService.deleteGenre(1L); });
    }

    private List<Genre> getGenreList() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(getGenre(1L));
        genres.add(getGenre(2L));
        genres.add(getGenre(3L));
        return genres;
    }

    private Genre getGenre(long id) {
        return new Genre(id, "Genre" + id);
    }
}
