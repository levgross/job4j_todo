package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;

    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("FROM Task");
        List<Task> rsl = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public List<Task> findByDone(boolean isDone) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("FROM Task WHERE done = :tDone")
                .setParameter("tDone", isDone);
        List<Task> rsl = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Task> query = session.createQuery("FROM Task WHERE id = :tId")
                .setParameter("tId", id);
        Optional<Task> rsl = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return task;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery(
                            "DELETE Task WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl != 0;
    }

    public boolean replace(int id, Task task) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery(
                            "UPDATE Task SET description = :tDesc, created = :tCrt, done = false WHERE id = :tId")
                    .setParameter("tDesc", task.getDescription())
                    .setParameter("tCrt", LocalDateTime.now())
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl != 0;
    }

    public void setDone(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET done = true WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
