package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    @MockBean
    private FilmController filmController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldGetAllFilms() throws Exception {
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

        Mockito.when(filmController.getAllFilms()).thenReturn(Set.of(film1, film2));

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(film1, film2))));
    }

    @Test
    void shouldPostFilm() throws Exception {
        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(2000, 5, 28));

        Mockito.when(filmController.postFilm(Mockito.any())).thenReturn(film);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(film)));
    }

    @Test
    void shouldUpdateFilm() throws Exception {
        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(2000, 5, 28));

        Mockito.when(filmController.postFilm(Mockito.any())).thenReturn(film);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(film)));

        film.setDescription("not so cool");

        Mockito.when(filmController.updateFilm(Mockito.any())).thenReturn(film);

        mockMvc.perform(put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("not so cool"));
    }

    @Test
    void shouldCheckReleaseFilmDate() throws Exception {

        Film film = new Film();
        film.setId(1);
        film.setName("Titanic");
        film.setDescription("great movie");
        film.setDuration(180);
        film.setReleaseDate(LocalDate.of(1800, 5, 28));

        Mockito.when(filmController.postFilm(Mockito.any())).thenReturn(film);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResolvedException);
    }
}