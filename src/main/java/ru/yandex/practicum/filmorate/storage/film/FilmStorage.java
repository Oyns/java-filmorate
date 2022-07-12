package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Set;

public interface FilmStorage {

    Set<Film> getAllFilms();


    void postFilm(@Valid @RequestBody Film film);


    void updateFilm(@Valid @RequestBody Film film);
}
