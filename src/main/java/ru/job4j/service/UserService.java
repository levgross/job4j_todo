package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.store.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserStore store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByLoginAndPwd(String login, String password) {
        return store.findByLoginAndPwd(login, password);
    }
}
