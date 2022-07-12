package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Set;

public interface UserStorage {
    Set<User> getAllUsers();

    void createUser(@Valid @RequestBody User user);

    void updateUser(@Valid @RequestBody User user);
}
