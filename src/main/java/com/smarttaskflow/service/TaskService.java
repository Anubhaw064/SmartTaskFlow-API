
package com.smarttaskflow.service;

import com.smarttaskflow.model.TaskPriority;
import com.smarttaskflow.model.Task;
import com.smarttaskflow.model.TaskStatus;
import com.smarttaskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }
    public List<Task> getTasksByPriority(TaskPriority priority) {
        return repository.findByPriority(priority);
    }

    public Task createTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        task.setCompleted(false);
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = repository.findById(id).orElseThrow();

        task.setStatus(status);
        task.setCompleted(status == TaskStatus.COMPLETED);

        return repository.save(task);
    }
}
