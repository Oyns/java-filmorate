package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private int id;
    private final Set<User> users = new HashSet<>();

    @Override
    public Set<User> getAllUsers() {
        return users;
    }

    @Override
    public void createUser(@Valid @RequestBody User user) {
        if (user.getId() < 0) {
            throw new ValidationException("id не может быть отрицательным");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        for (User i : users) {
            if (i.getEmail().equals(user.getEmail())) {
                throw new ValidationException("Пользователь с таким электронным адресом уже зарегестрирован");
            }
        }
        id++;
        user.setId(id);
        users.add(user);

    }

    @Override
    public void updateUser(@Valid @RequestBody User user) {
        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                users.remove(user1);
            } else {
                throw new ObjectNotFoundException("Пользователя с таким id нет.");
            }
        }
        users.add(user);
    }

}
