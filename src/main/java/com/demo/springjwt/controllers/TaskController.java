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
    
    @Autowired
    private TaskRepository taskRepository;


    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }   
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            // Mise à jour du statut de la tâche avec le statut de la tâche mise à jour
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        } else {
            return null; // Gérer le cas où la tâche n'existe pas avec une réponse appropriée
        }
    }
  
   
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }    
    @PostMapping("/{taskId}/responses")
    public Response addResponse(@PathVariable Long taskId, @RequestBody Response addResponse) {
        String responder = addResponse.getResponder();
        String response = addResponse.getResponse();

        return taskService.addResponse(taskId, responder, response);
    }

}