package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int id;
    private final Set<Film> films = new HashSet<>();

    @GetMapping
    public Set<Film> getAllFilms() {
        return films;
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        if (film.getId() < 0) {
            throw new ValidationException("id не может быть отрицательным");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
            throw new ValidationException("Поле <Дата релиза> должно быть позже 28.12.1895");
        }
        id++;
        film.setId(id);
        films.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        try {
            for (Film i : films) {
                if (i.getId() == film.getId()) {
                    films.remove(i);
                } else {
                    throw new ValidationException("Фильма с таким id нет");
                }
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
                throw new ValidationException("Поле <Дата релиза> должно быть позже 28.12.1895");
            }
            films.add(film);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return film;
    }
}
