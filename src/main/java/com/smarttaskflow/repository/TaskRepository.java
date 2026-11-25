package com.smarttaskflow.repository;

import com.smarttaskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smarttaskflow.model.TaskPriority;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByDeadlineLessThanEqual(LocalDate deadline);
}
