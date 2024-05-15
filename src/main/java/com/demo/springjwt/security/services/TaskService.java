package com.demo.springjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springjwt.models.Response;
import com.demo.springjwt.models.Task;
import com.demo.springjwt.models.TaskStatus;
import com.demo.springjwt.repository.ResponseRepository;
import com.demo.springjwt.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
  
    @Autowired 
    private ResponseRepository responseRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    } 

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setStatus(status);
            return taskRepository.save(task);
        } else {
            return null; 
        }  
    }   

    public Response addResponse(Long taskId, String responder, String response) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            Response newResponse = new Response();
            newResponse.setTaskId(taskId);
            newResponse.setResponder(responder);
            newResponse.setResponse(response);
            return responseRepository.save(newResponse);
        } else { 
            return null; 
        }
    } 

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
