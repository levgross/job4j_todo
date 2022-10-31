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

    public List<Task> findAllNew() {
        return store.findAllNew();
    }

    public List<Task> findAllDone() {
        return store.findAllDone();
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    public Task add(Task task) {
        return store.add(task);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public boolean replace(int id, Task task) {
        return store.replace(id, task);
    }

    public void setDone(int id) {
        store.setDone(id);
    }
}
