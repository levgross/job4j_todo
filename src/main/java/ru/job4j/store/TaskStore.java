package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final CrudStore crudStore;

    public List<Task> findAll() {
        return crudStore.query(
                "from Task t join fetch t.user join fetch t.priority order by t.id",
                Task.class
        );
    }

    public List<Task> findByDone(boolean isDone) {
        return crudStore.query(
                "from Task t join fetch t.user join fetch t.priority where t.done = :isDone",
                Task.class,
                Map.of("isDone", isDone)
        );
    }

    public Optional<Task> findById(int id) {
        return crudStore.optional("from Task t join fetch t.user join fetch t.priority where t.id = :tId",
                Task.class,
                Map.of("tId", id)
        );
    }

    public Task add(Task task) {
        crudStore.run(session -> session.persist(task));
        var categories = task.getCategories();
        for (var category : categories) {
            crudStore.run(session -> session.persist(category));
        }
        return task;
    }

    public void delete(int id) {
        crudStore.run("delete from Task where id = :tId",
                Map.of("tId", id));
    }

    public void replace(int id, Task task) {
            crudStore.run("update Task set description = :tDesc, created = :tCrt, done = false,"
                            + " user_id = :tUser, priority_id = :tPriority where id = :tId",
                    Map.of("tId", id, "tDesc", task.getDescription(), "tCrt", LocalDateTime.now(),
                            "tUser", task.getUser().getId(), "tPriority", task.getPriority().getId()));
    }

    public void setDone(int id) {
            crudStore.run("update Task set done = true where id = :tId",
                    Map.of("tId", id));
    }
}
