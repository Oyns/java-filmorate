package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final LocalDate FIRST_FILM = LocalDate.of(1895, 12, 25);

    private int id;
    private final Set<Film> films = new HashSet<>();

    @Override
    public Set<Film> getAllFilms() {
        return films;
    }

    @Override
    public void postFilm(Film film) {
        if (film.getId() < 0) {
            throw new ValidationException("id не может быть отрицательным");
        }
        checkDate(film);
        id++;
        film.setId(id);
        films.add(film);
    }

    @Override
    public void updateFilm(Film film) {
        for (Film film1 : films) {
            if (film1.getId() == film.getId()) {
                films.remove(film1);
            } else {
                throw new ObjectNotFoundException("Фильма с таким id нет");
            }
        }
        checkDate(film);
        films.add(film);
    }

    private void checkDate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM)) {
            throw new ValidationException("Поле <Дата релиза> должно быть позже 28.12.1895");
        }
    }
}
