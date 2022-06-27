package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {


    @Test
    void shouldGetAllUsers() {
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
        UserController userController = new UserController();
        userController.createUser(user1);
        userController.createUser(user2);
        assertEquals(2, userController.getAllUsers().size());
    }

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setId(1);
        user.setName("Oppo");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));
        UserController userController = new UserController();
        User result = userController.createUser(user);
        assertEquals(result, user);
    }

    @Test
    void shouldCreateUnknownUser() {
        User user = new User();
        user.setId(1);
        user.setName("");
        user.setName("");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));
        UserController userController = new UserController();
        User result = userController.createUser(user);
        assertEquals(result, user);
        assertEquals("Oppo", user.getName());
    }

    @Test
    void shouldCheckCreateUserWithNegativeId() {
       try {
           User user = new User();
           user.setId(-1);
           user.setName("");
           user.setName("");
           user.setEmail("boba@yandex.ru");
           user.setLogin("Oppo");
           user.setBirthday(LocalDate.of(1988, 5, 28));
           UserController userController = new UserController();
           userController.createUser(user);
       } catch (ValidationException e) {
           final String expected = "id не может быть отрицательным";
           assertEquals(expected, e.getMessage());
       }
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setName("Oppo");
        user.setEmail("boba@yandex.ru");
        user.setLogin("Oppo");
        user.setBirthday(LocalDate.of(1988, 5, 28));
        UserController userController = new UserController();
        userController.createUser(user);
        user.setEmail("car@mail.ru");
        User result = userController.updateUser(user);
        assertEquals("car@mail.ru", user.getEmail());
        assertEquals(result, user);
    }
}