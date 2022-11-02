package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {
    private final SessionFactory sf;

    public Optional<User> add(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> findByLoginAndPwd(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        var query = session.createQuery("FROM User WHERE login = :uLgn AND password = :uPwd")
                .setParameter("uLgn", login)
                .setParameter("uPwd", password);
        var rsl = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }
}
