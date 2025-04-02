package com.task.taskagain.controller;

import com.task.taskagain.dto.TaskRequest;
import com.task.taskagain.model.Task;
import com.task.taskagain.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // ✅ Only admins can assign tasks
    public ResponseEntity<?> assignTask(@RequestBody TaskRequest request) {
        Task task = taskService.assignTask(request.getUserId(), request.getDescription());
        return ResponseEntity.ok(task);
    }

    // ✅ ADD THIS: Fetch tasks for the logged-in user
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // ✅ Both users & admins can fetch tasks
    public ResponseEntity<List<Task>> getUserTasks() {
        List<Task> tasks = taskService.getUserTasks();
        return ResponseEntity.ok(tasks);
    }

    // 🔹 User marks a task as completed
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/complete/{taskId}")
    public ResponseEntity<String> completeTask(@PathVariable Long taskId) {
        taskService.completeTask(taskId);
        return ResponseEntity.ok("Task completed successfully");
    }
}
