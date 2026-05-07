package com.example.services;

import com.example.entity.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private Map<Long, Task> taskById = new HashMap<>();
    private List<Task> taskList = new ArrayList<>();
    private AtomicLong counterId = new AtomicLong(1);

    public Task createTask(String title, String description) {
        Long newId = counterId.getAndIncrement();
        Task newTask = new Task(newId, title, description);

        taskById.put(newId, newTask);
        taskList.add(newTask);

        return newTask;
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
