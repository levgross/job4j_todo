package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.service.CategoryService;
import ru.job4j.service.PriorityService;
import ru.job4j.service.TaskService;
import ru.job4j.util.Utility;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String tasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", taskService.findAll(user));
        model.addAttribute("user", Utility.check(session));
        return "tasks";
    }

    @GetMapping("/undone")
    public String undone(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("undone", taskService.findByDone(false, user));
        model.addAttribute("user", Utility.check(session));
        return "undone";
    }

    @GetMapping("/done")
    public String done(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("done", taskService.findByDone(true, user));
        model.addAttribute("user", Utility.check(session));
        return "done";
    }

    @GetMapping("/new")
    public String add(Model model, HttpSession session) {
        model.addAttribute("user", Utility.check(session));
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session,
                         @RequestParam("category_ids") Integer[] categories) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        task.setCreated(LocalDateTime.now());
        Arrays.stream(categories)
                .map(categoryService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(task::addCategory);
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/info/{id}")
    public String info(Model model, @PathVariable("id") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Task> optTask = taskService.findById(id, user);
        if (optTask.isEmpty()) {
            return "404";
        }
        Task task = optTask.get();
        model.addAttribute("task", task);
        model.addAttribute("categories", task.getCategories());
        model.addAttribute("user", Utility.check(session));
        return "info";
    }

    @GetMapping("/setDone/{id}")
    public String setDone(@PathVariable("id") int id) {
        taskService.setDone(id);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id, HttpSession session) {
        Optional<Task> optTask = taskService.findById(id);
        if (optTask.isEmpty()) {
            return "404";
        }
        model.addAttribute("task", optTask.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("user", Utility.check(session));
        return "formUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, HttpSession session,
                         @RequestParam("category_ids") Integer[] categories) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        task.setCreated(LocalDateTime.now());
        task.setCategories(new ArrayList<>());
        Arrays.stream(categories)
                .map(categoryService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(task::addCategory);
        taskService.replace(task);
        return "redirect:/tasks";
    }
}
