package com.smarttaskflow.controller;

import com.smarttaskflow.model.TaskStatus;
import com.smarttaskflow.model.Task;
import com.smarttaskflow.model.TaskPriority;
import com.smarttaskflow.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getTasks() {
        return service.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }

    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestParam TaskStatus status) {
        return service.updateStatus(id, status);
    }
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable TaskPriority priority) {
        return service.getTasksByPriority(priority);
    }
    @GetMapping("/deadline")
    public List<Task> getTasksByDeadline(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate deadline) {

        return service.getTasksByDeadline(deadline);
    }
}
