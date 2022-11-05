package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {
    private final CrudStore crudStore;

    public Optional<User> add(User user) {
        try {
            crudStore.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(int id) {
        return crudStore.optional("from User where id = :uId", User.class,
                Map.of("uId", id));
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional(
                "from User where login = :uLgn and password = :uPwd", User.class,
                Map.of("uLgn", login, "uPwd", password)
        );
    }
}
