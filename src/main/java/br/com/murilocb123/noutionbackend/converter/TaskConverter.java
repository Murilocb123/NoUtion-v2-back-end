package br.com.murilocb123.noutionbackend.converter;

import br.com.murilocb123.noutionbackend.dto.Task;
import br.com.murilocb123.noutionbackend.entities.TaskEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskConverter {

    public static TaskEntity toEntity(Task task, TaskEntity taskEntity) {
        if (task == null) {
            return null;
        }

        taskEntity.setId(task.id());
        taskEntity.setTitle(task.title());
        taskEntity.setContent(task.content());
        taskEntity.setStatus(task.status());
        taskEntity.setTags(task.tags());
        return taskEntity;
    }

    public static Task toDto(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        return new Task(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getContent(),
                taskEntity.getStatus(),
                taskEntity.getTags(),
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt()
        );
    }
}


