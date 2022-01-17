package chewyt.todo.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import chewyt.todo.TodoApplication;
import chewyt.todo.service.TaskService;

@Controller
public class toDoController {

    @Autowired
    TaskService service;

    Logger logger  = Logger.getLogger(TodoApplication.class.getName());

    @GetMapping("/")
    public String getHome() {
        return "login";
    }

    @PostMapping(value = "/user")
    public String loginUser(@RequestBody MultiValueMap<String, String> loginForm, Model model) {

        String user = loginForm.getFirst("user");
        logger.log(Level.INFO, "User: %s".formatted(user));
        List<String> savedList = service.get(user);
        logger.log(Level.INFO, savedList.toString());

        model.addAttribute("savedTasks", savedList);
        model.addAttribute("user", user);
        return "index";
    }

}
