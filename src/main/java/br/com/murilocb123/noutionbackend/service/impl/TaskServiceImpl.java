package br.com.murilocb123.noutionbackend.service.impl;

import br.com.murilocb123.noutionbackend.converter.TaskConverter;
import br.com.murilocb123.noutionbackend.dto.Task;
import br.com.murilocb123.noutionbackend.entities.TaskEntity;
import br.com.murilocb123.noutionbackend.enums.StatusEntity;
import br.com.murilocb123.noutionbackend.repositories.TaskRepository;
import br.com.murilocb123.noutionbackend.service.TaskService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public UUID createOrUpdateTask(Task task) {
        TaskEntity taskEntity;
        if (task.id() != null) {
            taskEntity = taskRepository.findById(task.id()).orElse(new TaskEntity());
        } else {
            taskEntity = new TaskEntity();
        }
        var entity = TaskConverter.toEntity(task, taskEntity);
        entity.setId(task.id());

        return taskRepository.save(taskEntity).getId();
    }

    @Override
    public void updateStatusTask(UUID id, StatusEntity status) {
        var taskEntity = taskRepository.findById(id);
        if (taskEntity.isPresent()) {
            taskEntity.get().setStatus(status);
            taskRepository.save(taskEntity.get());
        }
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }



    @Override
    public List<Task> listAllTasks() {
        return taskRepository.findAll().stream().map(TaskConverter::toDto).toList();
    }

    @Override
    public Task getTaskById(UUID id) {
        return TaskConverter.toDto(taskRepository.findById(id).orElseThrow());
    }
}
