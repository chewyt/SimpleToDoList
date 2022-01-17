package chewyt.todo.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    
    private String task;
    public static List<String> taskList =  new ArrayList<>();

    public Task(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    
}
