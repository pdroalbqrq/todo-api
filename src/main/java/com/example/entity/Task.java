package com.example.entity;

public class Task {

    private Long id;
    private String title;
    private String description;
    private boolean done;

    public Task(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = false;  // Por padrão, tarefa começa não concluída
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {  // 'is' para booleanos
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Tarefa{id=" + id + ", titulo='" + title + "', concluida=" + done + "}";
    }
}
