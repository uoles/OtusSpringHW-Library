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

    private final long ID_1 = 1L;
    private final long ID_2 = 2L;
    private final long ID_3 = 3L;

    private final int OBJECT_COUNT_3 = 3;

    private final String GENRE_UPDATED_NAME = "UpdatedName";
    private final String GENRE_NAME = "GenreName";

    @Mock
    private GenreDao genreDao;

    @InjectMocks
    private GenreManageServiceImpl genreManageService;

    @Test
    @DisplayName("Получение жанра по id")
    void getGenreById() {
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(ID_1)) );
        Genre genre = genreManageService.getGenreById(ID_1);

        assertThat(genre).isNotNull();
        assertThat(genre).isEqualTo(getGenre(ID_1));
    }

    @Test
    @DisplayName("Получение всех жанров")
    void getGenres() {
        when(genreDao.findAll()).thenReturn( getGenreList() );
        List<Genre> authors = genreManageService.getGenres();

        assertThat(authors).isNotNull();
        assertThat(authors).hasSize(OBJECT_COUNT_3);
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

        long id = genreManageService.addGenre(GENRE_NAME);

        assertThat(id).isEqualTo(ID_2);
    }

    @Test
    @DisplayName("Обновление жанра")
    void updateGenre() {
        when(genreDao.save(any(Genre.class))).then(new Answer<Genre>() {

            @Override
            public Genre answer(InvocationOnMock invocationOnMock) throws Throwable {
                Genre genre = (Genre) invocationOnMock.getArgument(0);
                genre.setId(ID_1);
                return genre;
            }
        });
        when(genreDao.findById(anyLong())).thenReturn( Optional.of(getGenre(ID_1)) );

        int count = genreManageService.updateGenre(ID_1, GENRE_UPDATED_NAME);

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление жанра, который используется в таблице книг")
    void deleteGenre() {
        doThrow(DataIntegrityViolationException.class).when(genreDao).deleteById(ID_1);
        assertThrows(DataIntegrityViolationException.class, () -> { genreManageService.deleteGenre(ID_1); });
    }

    private List<Genre> getGenreList() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(getGenre(ID_1));
        genres.add(getGenre(ID_2));
        genres.add(getGenre(ID_3));
        return genres;
    }

    private Genre getGenre(long id) {
        return new Genre(id, GENRE_NAME + id);
    }
}
