package com.example.task.services;

import com.example.task.entity.Task;
import com.example.user.services.UserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private Map<Long, Task> taskById = new HashMap<>();
    private List<Task> taskList = new ArrayList<>();
    private AtomicLong counterId = new AtomicLong(1);

    private final UserService userService;

    public TaskService(UserService userService) {
        this.userService = userService;
    }

    public Task createTask(String title, String description, Long userId) {
        userService.searchById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário com id " + userId + " não encontrado."));

        Long newId = counterId.getAndIncrement();
        Task newTask = new Task(newId, title, description, userId);

        taskById.put(newId, newTask);
        taskList.add(newTask);

        return newTask;
    }

    public List<Task> listByUser(Long userId) {
        userService.searchById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário com id " + userId + " não encontrado."));

        return taskList.stream()
                .filter(t -> t.getUserId().equals(userId))
                .toList();
    }

    public List<Task> listAll() {
        return new ArrayList<>(taskList);
    }

    public Optional<Task> searchById(Long id) {
        return Optional.ofNullable(taskById.get(id));
    }

    public boolean taskDone(Long id) {
        Task task = taskById.get(id);
        if (task == null) return false;
        task.setDone(true);
        return true;
    }


    public boolean deleteTask(Long id) {
        Task removed = taskById.remove(id);
        if (removed != null) {
            taskList.remove(removed);
            return true;
        }
        return false;
    }


    public List<Task> listarTasksConcluidas() {

        return taskList.stream()
                .filter(Task::isDone)
                .toList();
    }
}
