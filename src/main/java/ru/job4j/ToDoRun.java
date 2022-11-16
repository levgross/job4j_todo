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
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var stored = listOf("from User where id = 10", User.class, sf);
            for (User user : stored) {
                System.out.println(user.getTimezone().getDisplayName());

                System.out.println(LocalDateTime.now() + " : " + LocalDateTime.now().atZone(ZoneId.of("UTC+8")).format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd")));


            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
//        var zones = new ArrayList<TimeZone>();
//        for (String timeId : TimeZone.getAvailableIDs()) {
//            zones.add(TimeZone.getTimeZone(timeId));
//        }
//        for (TimeZone zone : zones) {
//            System.out.println(zone.getID() + " : " + zone.getDisplayName());
//        }

    }

    public static <T> List<T> listOf(String query, Class<T> model, SessionFactory sf) {
        Session session = sf.openSession();
        var rsl = session.createQuery(query, model)
                .getResultList();
        session.close();
        return rsl;
    }
}
