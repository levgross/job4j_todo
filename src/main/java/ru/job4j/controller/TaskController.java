package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    @GetMapping("")
    public String tasks(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    @GetMapping("/undone")
    public String undone(Model model) {
        model.addAttribute("undone", service.findByDone(false));
        return "undone";
    }

    @GetMapping("/done")
    public String done(Model model) {
        model.addAttribute("done", service.findByDone(true));
        return "done";
    }

    @GetMapping("/new")
    public String add(Model model) {
        return "new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/info/{id}")
    public String info(Model model, @PathVariable("id") int id) {
        Optional<Task> optTask = service.findById(id);
        if (optTask.isEmpty()) {
            return "404";
        }
        model.addAttribute("task", optTask.get());
        return "info";
    }

    @GetMapping("/setDone/{id}")
    public String setDone(@PathVariable("id") int id) {
        service.setDone(id);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        if (!service.delete(id)) {
            return "404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Optional<Task> optTask = service.findById(id);
        if (optTask.isEmpty()) {
            return "404";
        }
        model.addAttribute("task", optTask.get());
        return "formUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/tasks";
    }
}
