package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    @Test
    void shouldGetAllFilms() {
        Film film1 = new Film();
        film1.setId(1);
        film1.setName("Titanic");
        film1.setDescription("great movie");
        film1.setDuration(180);
        film1.setReleaseDate(LocalDate.of(2000, 5, 28));
        Film film2 = new Film();
        film2.setId(2);
        film2.setName("Avatar");
        film2.setDescription("blue guys");
        film2.setDuration(240);
        film2.setReleaseDate(LocalDate.of(2002, 1, 12));
        FilmController filmController = new FilmController();
        filmController.postFilm(film1);
        filmController.postFilm(film2);
        assertEquals(2, filmController.getAllFilms().size());
    }

    @Test
    void shouldPostFilm() {
        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(2000, 5, 28));
        FilmController filmController = new FilmController();
        Film result = filmController.postFilm(film);
        assertEquals(result, film);
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(2000, 5, 28));
        FilmController filmController = new FilmController();
        filmController.postFilm(film);
        film.setDescription("not so cool");
        Film result = filmController.updateFilm(film);
        assertEquals(result, film);
        assertEquals("not so cool", film.getDescription());
    }

    @Test
    void shouldCheckReleaseFilmDate() {

        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(1800, 5, 28));
        FilmController filmController = new FilmController();

        assertThrows(ValidationException.class, () -> filmController.postFilm(film));
    }
}