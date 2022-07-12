package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserController userController;

    @Test
    void shouldCheckGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setName("Oppo");
        user1.setEmail("boba@yandex.ru");
        user1.setLogin("Oppo");
        user1.setBirthday(LocalDate.of(1988, 5, 28));
        User user2 = new User();
        user2.setId(2);
        user2.setName("Xiaomi");
        user2.setEmail("mi@yandex.ru");
        user2.setLogin("w00t");
        user2.setBirthday(LocalDate.of(2002, 1, 12));

        Mockito.when(userController.getAllUsers()).thenReturn(Set.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(user1, user2))));
    }

    @Test
    void shouldGetUserDataById() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setName("Oppo");
        user1.setEmail("boba@yandex.ru");
        user1.setLogin("Oppo");
        user1.setBirthday(LocalDate.of(1988, 5, 28));

        Mockito.when(userController.getUserData(Mockito.any())).thenReturn(user1);

        mockMvc.perform(
                        get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Oppo"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Oppo");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));

        Mockito.when(userController.createUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }


    @Test
    void shouldCheckCreateUnknownUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("");
        user.setName("");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));

        Mockito.when(userController.createUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResolvedException);
    }

    @Test
    void shouldCheckCreateUserWithNegativeId() throws Exception {
        User user = new User();
        user.setId(-1);
        user.setName("");
        user.setName("");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));

        Mockito.when(userController.createUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MvcResult::getResolvedException);
    }

    @Test
    void shouldCheckUpdateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Oppo");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));

        Mockito.when(userController.createUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));

        user.setName("Bobby");
        Mockito.when(userController.updateUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Bobby"));
    }
}