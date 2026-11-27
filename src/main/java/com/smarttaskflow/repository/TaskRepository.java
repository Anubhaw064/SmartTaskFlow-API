package com.smarttaskflow.repository;

import com.smarttaskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smarttaskflow.model.TaskPriority;
import java.util.List;
import java.time.LocalDate;
import com.smarttaskflow.entity.User;
import java.util.Optional;



@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByDeadlineLessThanEqual(LocalDate deadline);
    List<Task> findByUser(User user);

    List<Task> findByUserAndPriority(User user, TaskPriority priority);

    List<Task> findByUserAndDeadlineLessThanEqual(User user, LocalDate deadline);

    Optional<Task> findByIdAndUser(Long id, User user);
}
