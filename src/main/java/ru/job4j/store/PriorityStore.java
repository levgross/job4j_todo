package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityStore {
    private final CrudStore crudStore;

    public Optional<Priority> findById(int id) {
        return crudStore.optional(
                "from Priority where id = :pId",
                Priority.class,
                Map.of("pId", id)
        );
    }

    public List<Priority> findAll() {
        return crudStore.query("from Priority order by id", Priority.class);
    }
}
