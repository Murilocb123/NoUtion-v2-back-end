package br.com.murilocb123.noutionbackend.repositories;

import br.com.murilocb123.noutionbackend.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
}
