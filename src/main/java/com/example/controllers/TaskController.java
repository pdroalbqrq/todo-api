package com.example.controllers;

import com.example.entity.Task;
import com.example.services.TaskService;
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
    public List<Task> listarAll() {
        return taskService.listAll();
    }

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request.getTitle(), request.getDescription());
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

        public String getTitle() { return title; }
        public String getDescription() { return description; }
    }
}