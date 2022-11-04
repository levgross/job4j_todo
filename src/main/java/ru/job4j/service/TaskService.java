package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.job4j.model.Task;
import ru.job4j.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore store;

    public List<Task> findAll() {
        return store.findAll();
    }

    public List<Task> findByDone(boolean isDone) {
        return store.findByDone(isDone);
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

    public void replace(int id, Task task) {
        store.replace(id, task);
    }

    public void setDone(int id) {
        store.setDone(id);
    }
}
