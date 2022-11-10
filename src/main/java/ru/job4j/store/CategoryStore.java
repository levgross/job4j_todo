package ru.job4j.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryStore {
    private final CrudStore crudStore;

    public Optional<Category> findById(int id) {
        return crudStore.optional(
                "from Category where id = :cId",
                Category.class,
                Map.of("cId", id)
        );
    }

    public List<Category> findAll() {
        return crudStore.query("from Category order by id", Category.class);
    }
}
