package br.com.murilocb123.noutionbackend.service;

import br.com.murilocb123.noutionbackend.dto.Task;
import br.com.murilocb123.noutionbackend.enums.StatusEntity;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    UUID createOrUpdateTask(Task task);
    void updateStatusTask(UUID id, StatusEntity status);
    void deleteTask(UUID id);
    List<Task> listAllTasks();
    Task getTaskById(UUID id);

}
