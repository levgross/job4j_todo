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
public class TaskController {
    private final TaskService service;

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    @GetMapping("/tasksNew")
    public String tasksNew(Model model) {
        model.addAttribute("newtasks", service.findAllNew());
        return "tasksNew";
    }

    @GetMapping("/tasksDone")
    public String tasksDone(Model model) {
        model.addAttribute("done", service.findAllDone());
        return "tasksDone";
    }

    @GetMapping("/addTask")
    public String addTask(Model model) {
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/taskInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id) {
        Optional<Task> optTask = service.findById(id);
        if (optTask.isEmpty()) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", optTask.get());
        return "taskInfo";
    }

    @GetMapping("/setDone/{taskId}")
    public String setDone(@PathVariable("taskId") int id) {
        service.setDone(id);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        service.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(Model model, @PathVariable("taskId") int id) {
        Optional<Task> optTask = service.findById(id);
        if (optTask.isEmpty()) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", optTask.get());
        return "formUpdateTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/tasks";
    }
}
