package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private int id;
    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> getAllUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getId() < 0) {
            throw new ValidationException("id не может быть отрицательным");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        try {
            for (User i : users) {
                if (i.getEmail().equals(user.getEmail())) {
                    throw new ValidationException("Пользователь с таким электронным адресом уже зарегестрирован");
                }
            }
            id++;
            user.setId(id);
            users.add(user);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        try {
            for (User i : users) {
                if (i.getId() == user.getId()) {
                    users.remove(i);
                } else {
                    throw new ValidationException("Пользователя с таким id нет");
                }
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        users.add(user);
        return user;
    }
}
