package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.User;
import ru.job4j.service.UserService;
import ru.job4j.util.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = service.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/tasks";
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model,
                              @RequestParam(name = "fail", required = false) Boolean fail,
                              HttpSession session) {
        model.addAttribute("user", Utility.check(session));
        model.addAttribute("fail", fail != null);
        model.addAttribute("zones", List.of(TimeZone.getAvailableIDs()));
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, @RequestParam("zoneId") String zoneId) {
        if ("Select your time zone".equals(zoneId)) {
            return "redirect:/formAddUser?fail=true";
        }
        user.setTimezone(TimeZone.getTimeZone(ZoneId.of(zoneId)));
        Optional<User> regUser = service.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/loginPage";
    }
}
