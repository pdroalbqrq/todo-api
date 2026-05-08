package com.example.task.controller;

import com.example.task.entity.Task;
import com.example.task.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> listAll() {
        return taskService.listAll();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.listByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request) {
        try {
            Task task = taskService.createTask(
                    request.getTitle(),
                    request.getDescription(),
                    request.getUserId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/concluir")
    public String taskDone(@PathVariable Long id) {
        boolean concluida = taskService.taskDone(id);
        if (concluida) {
            return "Tarefa " + id + " concluída com sucesso!";
        }
        return "Tarefa não encontrada!";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        boolean deletada = taskService.deleteTask(id);
        if (deletada) {
            return "Tarefa " + id + " removida!";
        }
        return "Tarefa não encontrada!";
    }

    public static class TaskRequest {
        private String title;
        private String description;
        private Long userId;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Long getUserId() { return userId; }
    }
}