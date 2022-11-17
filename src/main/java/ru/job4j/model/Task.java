package ru.job4j.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String description;
    private LocalDateTime created;
    private boolean done;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    @ToString.Exclude
    private Priority priority;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_category",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getTasks().add(this);
    }
}
