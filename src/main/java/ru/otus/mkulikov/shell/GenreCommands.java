package ru.otus.mkulikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mkulikov.app.model.Genre;
import ru.otus.mkulikov.app.service.GenreManageService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 30.05.2019
 * Time: 14:47
 */

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreManageService genreManageService;

    @ShellMethod(key = {"getGenreById"}, value = "Select genre by id.")
    public String getGenreById(@ShellOption String id) {
        Genre genre = genreManageService.getGenreById(id);
        return genre.toString();
    }

    @ShellMethod(key = {"getGenres"}, value = "Select all genres.")
    public String getGenres() {
        List<Genre> allObjects = genreManageService.getGenres();
        return allObjects.toString();
    }

    @ShellMethod(key = {"addGenre"}, value = "Add new genre.")
    public String addGenre(@ShellOption String name) {
        Genre genre = genreManageService.addGenre(name);
        return "Add " + (genre != null ? 1 : 0) + " row(s)\nID: " + genre.getId();
    }

    @ShellMethod(key = {"updateGenre"}, value = "Update genre by id.")
    public String updateGenre(@ShellOption String id, @ShellOption String name) {
        Genre genre = genreManageService.updateGenre(id, name);
        return "Updated " + (genre != null ? 1 : 0) + " row(s)\nID: " + genre.getId();
    }

    @ShellMethod(key = {"deleteGenre"}, value = "Delete genre by id.")
    public String deleteGenre(@ShellOption String id) {
        int count = genreManageService.deleteGenre(id);
        return "Deleted " + count + " row(s)";
    }
}
