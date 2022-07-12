package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User getUserData(String id) {
        negativeId(id);
        return userStorage.getAllUsers().stream()
                .filter(user -> user.getId() == Integer.parseInt(id))
                .findAny()
                .orElse(null);
    }


    public Set<User> getAllFriends(String id) {
        negativeId(id);
        Set<User> techSet = new HashSet<>();
        Set<Long> longs = userStorage.getAllUsers().stream()
                .filter(user -> user.getId() == Integer.parseInt(id))
                .findAny()
                .map(User::getFriends)
                .orElse(null);

        assert longs != null;
        for (Long i : longs) {
            for (User user : userStorage.getAllUsers()) {
                if (user.getId() == i) {
                    techSet.add(user);
                }
            }
        }
        return techSet;
    }

    public List<User> getCommonFriends(String id, String otherId) {
        User user1 = null;
        User user2 = null;
        List<User> finalList = new ArrayList<>();
        for (User user : userStorage.getAllUsers()) {
            if (user.getId() == Integer.parseInt(id)) {
                user1 = user;
            }
        }

        for (User user : userStorage.getAllUsers()) {
            if (user.getId() == Integer.parseInt(otherId)) {
                user2 = user;
            }
        }

        assert user1 != null;
        assert user2 != null;
        List<Long> commonFriendsIds = user1.getFriends().stream()
                .filter(user2.getFriends()::contains)
                .collect(Collectors.toList());

        for (Long i : commonFriendsIds) {
            for (User user : userStorage.getAllUsers()) {
                if (user.getId() == i) {
                    finalList.add(user);
                }
            }
        }
        return finalList;
    }

    public User addFriend(String id, String friendId) {
        negativeId(id);
        negativeId(friendId);
        userStorage.getAllUsers().stream()
                .filter(user -> user.getId() == Integer.parseInt(id))
                .forEach(user -> user.getFriends().add(Long.parseLong(friendId)));

        userStorage.getAllUsers().stream()
                .filter(user -> user.getId() == Integer.parseInt(friendId))
                .forEach(user -> user.getFriends().add(Long.parseLong(id)));

        return userStorage.getAllUsers().stream()
                .filter(user -> user.getId() == Integer.parseInt(id))
                .findAny()
                .orElse(null);
    }


    public void deleteFriend(String id, String friendId) {
        negativeId(id);
        negativeId(friendId);
        for (User i : userStorage.getAllUsers()) {
            if (i.getId() == Integer.parseInt(id)) {
                i.getFriends().remove(Long.valueOf(friendId));
            }
        }

        for (User i : userStorage.getAllUsers()) {
            if (i.getId() == Integer.parseInt(friendId)) {
                i.getFriends().remove(Long.valueOf(id));
            }
        }
    }


    private void negativeId(String id) {
        if (Integer.parseInt(id) < 0) {
            throw new ObjectNotFoundException("Значение id не может быть отрицательным");
        }
    }
}
