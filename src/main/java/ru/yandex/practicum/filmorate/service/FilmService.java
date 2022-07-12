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
        negativeId(id);
        return filmStorage.getAllFilms().stream()
                .filter(film -> film.getId() == Integer.parseInt(id))
                .findAny()
                .orElse(null);
    }


    public void userLikesFilm(String id, String userId) {
        for (Film i : filmStorage.getAllFilms()) {
            if (i.getId() == Integer.parseInt(id)) {
                i.getLikes().add(Long.valueOf(userId));
            }
        }
    }

    public List<Film> getBestFilms(String count) {
        List<Film> topRated = new ArrayList<>();
        int bestFilm = 0;
        if (count != null) {
            for (Film i : filmStorage.getAllFilms()) {
                topRated.add(i);
                topRated.sort(new CompareFilms());
                if (topRated.size() > Integer.parseInt(count)) {
                    for (int j = topRated.size(); j > Integer.parseInt(count); j--) {
                        topRated.remove(j - 1);
                    }
                }
            }
        } else {
            for (Film i : filmStorage.getAllFilms()) {
                bestFilm++;
                if (i.getLikes().size() > bestFilm || topRated.size() <= 10) {
                    topRated.add(i);
                }
            }
        }
        return topRated;
    }

    public void deleteUserLike(String id, String userId) {
        negativeId(id);
        negativeId(userId);
        for (Film i : filmStorage.getAllFilms()) {
            if (i.getId() == Integer.parseInt(userId)) {
                i.getLikes().remove(Long.valueOf(id));
            }
        }
    }

    private void negativeId(String id) {
        if (Integer.parseInt(id) < 0) {
            throw new ObjectNotFoundException("Значение id не может быть отрицательным");
        }
    }

    static class CompareFilms implements Comparator<Film> {

        @Override
        public int compare(Film o1, Film o2) {
            return Integer.compare(o2.getLikes().size(), o1.getLikes().size());
        }
    }
}

