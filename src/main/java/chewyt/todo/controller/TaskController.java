package chewyt.todo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import chewyt.todo.TodoApplication;

import chewyt.todo.service.TaskService;

@Controller
@RequestMapping(path = "/task", produces = MediaType.TEXT_HTML_VALUE)
public class TaskController {
    private final Logger logger = Logger.getLogger(TodoApplication.class.getName());

    @Autowired
    TaskService service;

    @PostMapping
    public String addTask(@RequestBody MultiValueMap<String, String> form, Model model) {
        String task = form.getFirst("task");
        logger.log(Level.INFO, "Task Name: %s".formatted(task));
        String contents = form.getFirst("contents");
        logger.log(Level.INFO, "Contents before adding: %s".formatted(contents));

        List<String> tasks = new LinkedList<>();

        // Split the contents into List , delimited by |

        if (!contents.equals("")) {
            // append new tasks to contents
            if (!task.equals("")) {
                contents = "%s%s%s".formatted(contents, ";", task);
            }
            tasks = Arrays.asList(contents.split(";"));
        } else {
            if (!task.equals("")) {
                contents = task; // first task added to empty contents
                tasks.add(task);
            }
        }

        logger.log(Level.INFO, "Contents after adding: %s".formatted(contents));
        logger.log(Level.INFO, "List view after adding: %s".formatted(tasks.toString()));
        List<String> savedList = service.get(form.getFirst("user"));
        model.addAttribute("savedTasks", savedList);
        model.addAttribute("tasks", tasks);
        model.addAttribute("contents", contents);
        model.addAttribute("user", form.getFirst("user"));
        return updateHome();
    }

    @PostMapping("record")
    public String postTaskSave(@RequestBody MultiValueMap<String, String> form, Model model) {
        String contents = form.getFirst("contents");
        logger.log(Level.INFO, "to be saved : %s".formatted(contents));
        List<String> tasksList = new ArrayList<String>(Arrays.asList(contents.split(";")));
        logger.log(Level.INFO, String.format("Size of task list: %d", tasksList.size()));
        if (!contents.equals("")) {

            service.saveList(form.getFirst("user"), tasksList);
        }
        contents = null;
        tasksList.clear();
        List<String> savedList = service.get(form.getFirst("user"));
        model.addAttribute("tasks", tasksList);
        model.addAttribute("savedTasks", savedList);
        model.addAttribute("contents", contents);
        model.addAttribute("user", form.getFirst("user"));
        return updateHome();
    }

    @GetMapping
    public String updateHome() {
        return "index";
    }
}
