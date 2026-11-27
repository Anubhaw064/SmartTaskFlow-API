
package com.smarttaskflow.service;

import com.smarttaskflow.entity.User;
import com.smarttaskflow.model.Task;
import com.smarttaskflow.model.TaskPriority;
import com.smarttaskflow.model.TaskStatus;
import com.smarttaskflow.repository.TaskRepository;
import com.smarttaskflow.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.time.LocalDate;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository repository,
                       UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User not found");
        }

        return user;
    }

    public List<Task> getAllTasks() {
        return repository.findByUser(getCurrentUser());
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return repository.findByUserAndPriority(
                getCurrentUser(), priority);
    }

    public List<Task> getTasksByDeadline(LocalDate deadline) {
        return repository.findByUserAndDeadlineLessThanEqual(
                getCurrentUser(), deadline);
    }

    public Task createTask(Task task) {
        task.setId(null);
        task.setUser(getCurrentUser());
        task.setStatus(TaskStatus.PENDING);
        task.setCompleted(false);

        return repository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = repository.findByIdAndUser(id, getCurrentUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task not found"));

        repository.delete(task);
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = repository.findByIdAndUser(id, getCurrentUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task not found"));

        task.setStatus(status);
        task.setCompleted(status == TaskStatus.COMPLETED);

        return repository.save(task);
    }
}
