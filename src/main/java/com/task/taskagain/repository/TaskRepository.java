package com.task.taskagain.repository;

import com.task.taskagain.model.Task;
import com.task.taskagain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
