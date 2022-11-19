package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.store.TaskStore;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore store;

    public List<Task> findAll(User user) {
        return createdWithUserTimezone(store.findAll(), user);
    }

    public List<Task> findByDone(boolean isDone, User user) {
        return createdWithUserTimezone(store.findByDone(isDone), user);
    }

    public Optional<Task> findById(int id, User user) {
        return createdWithUserTimezone(store.findById(id), user);
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    public Task add(Task task) {
        return store.add(task);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public void replace(Task task) {
        store.replace(task);
    }

    public void setDone(int id) {
        store.setDone(id);
    }

    private List<Task> createdWithUserTimezone(List<Task> list, User user) {
        var tzId = user.getTimezone().getID();
        for (Task task : list) {
            var time = task.getCreated().atZone(ZoneId.of("UTC"));
            var zonedDateTime = time.withZoneSameInstant(ZoneId.of(tzId));
            task.setCreated(zonedDateTime.toLocalDateTime());
        }
        return list;
    }

    private Optional<Task> createdWithUserTimezone(Optional<Task> task, User user) {
        if (task.isPresent()) {
            var tzId = user.getTimezone().getID();
            var time = task.get().getCreated().atZone(ZoneId.of("UTC"));
            var zonedDateTime = time.withZoneSameInstant(ZoneId.of(tzId));
            task.get().setCreated(zonedDateTime.toLocalDateTime());
        }
        return task;
    }
}
