package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Validated
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;

    private final FilmService filmService;
    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Set<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        filmStorage.postFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmStorage.updateFilm(film);
        return film;
    }

    @GetMapping("{id}")
    public Film getFilmData(@PathVariable String id) {
        return filmService.getFilmData(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void userLikesFilm(@PathVariable String id,
                              @PathVariable String userId) {
        filmService.userMakesLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.getBestFilms(count);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteUserLike(@PathVariable String id,
                               @PathVariable String userId) {
        filmService.deleteUserLike(id, userId);
    }
}
