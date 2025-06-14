package br.com.murilocb123.noutionbackend.controller;


import br.com.murilocb123.noutionbackend.dto.Task;
import br.com.murilocb123.noutionbackend.entities.TaskEntity;
import br.com.murilocb123.noutionbackend.enums.StatusEntity;
import br.com.murilocb123.noutionbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<UUID> createOrUpdateTask(@RequestBody Task task) {
        UUID id = taskService.createOrUpdateTask(task);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<Task>> listAllTasks() {
        return ResponseEntity.ok(taskService.listAllTasks());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatusTask(@PathVariable UUID id, @RequestParam StatusEntity status) {
        taskService.updateStatusTask(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}