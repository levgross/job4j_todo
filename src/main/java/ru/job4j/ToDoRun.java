package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ToDoRun {
    public static void main(String[] args) {
        var tz = TimeZone.getTimeZone("America/Los_Angeles").getID();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var stored = listOf("from Task", Task.class, sf);
            for (Task task : stored) {
                var time = task.getCreated().atZone(ZoneId.of(tz));
                task.setCreated(time.toLocalDateTime());
                System.out.println("LocalDateTime: " + task.getCreated() + " - ZonedDateTime: " + time);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> List<T> listOf(String query, Class<T> model, SessionFactory sf) {
        Session session = sf.openSession();
        var rsl = session.createQuery(query, model)
                .getResultList();
        session.close();
        return rsl;
    }
}
