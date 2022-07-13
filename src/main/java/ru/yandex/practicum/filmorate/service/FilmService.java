package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilmData(String id) {
        checkId(id);
        return filmStorage.getAllFilms().stream()
                .filter(film -> film.getId() == Integer.parseInt(id))
                .findAny()
                .orElse(null);
    }


    public void userMakesLike(String id, String userId) {
        for (Film film : filmStorage.getAllFilms()) {
            if (film.getId() == Integer.parseInt(id)) {
                film.getLikes().add(Long.valueOf(userId));
            }
        }
    }

    public List<Film> getBestFilms(String count) {
        List<Film> topRated = new ArrayList<>();
        int bestFilm = 0;
        if (count != null) {
            for (Film film : filmStorage.getAllFilms()) {
                topRated.add(film);
                topRated.sort(new CompareFilms());
                if (topRated.size() > Integer.parseInt(count)) {
                    for (int i = topRated.size(); i > Integer.parseInt(count); i--) {
                        topRated.remove(i - 1);
                    }
                }
            }
        } else {
            for (Film film : filmStorage.getAllFilms()) {
                bestFilm++;
                if (film.getLikes().size() > bestFilm || topRated.size() <= 10) {
                    topRated.add(film);
                }
            }
        }
        return topRated;
    }

    public void deleteUserLike(String id, String userId) {
        checkId(id);
        checkId(userId);
        for (Film film : filmStorage.getAllFilms()) {
            if (film.getId() == Integer.parseInt(userId)) {
                film.getLikes().remove(Long.valueOf(id));
            }
        }
    }

    private void checkId(String id) {
        if (Integer.parseInt(id) < 0) {
            throw new ObjectNotFoundException("Значение id не может быть отрицательным");
        }
    }

    static class CompareFilms implements Comparator<Film> {

        @Override
        public int compare(Film film1, Film film2) {
            return Integer.compare(film2.getLikes().size(), film1.getLikes().size());
        }
    }
}

