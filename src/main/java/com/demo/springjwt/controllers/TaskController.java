package com.demo.springjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.demo.springjwt.models.Response;
import com.demo.springjwt.models.Task;
import com.demo.springjwt.models.TaskStatus;
import com.demo.springjwt.repository.ResponseRepository;
import com.demo.springjwt.repository.TaskRepository;
import com.demo.springjwt.security.services.TaskService;

import java.util.List;
 
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
   


    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }  

    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }
   
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
    @PostMapping("/{taskId}/responses")
    public Response addResponse(@PathVariable Long taskId, @RequestParam String responder, @RequestParam String response) {
        return taskService.addResponse(taskId, responder, response);
    }
}