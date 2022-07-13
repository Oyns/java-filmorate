package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Validated
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage userStorage;

    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public Set<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        userStorage.createUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        userStorage.updateUser(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUserData(@PathVariable String id) {
        return userService.getUserData(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getAllFriends(@PathVariable String id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@RequestBody @PathVariable String id,
                          @PathVariable String friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id,
                             @PathVariable String friendId) {
        userService.deleteFriend(id, friendId);
    }
}
