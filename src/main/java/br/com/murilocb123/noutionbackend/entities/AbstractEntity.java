package br.com.murilocb123.noutionbackend.entities;

import br.com.murilocb123.noutionbackend.infra.security.UserContextHolder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TenantId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @TenantId
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PreUpdate
    @PreRemove
    @PrePersist
    public void setUserId() {
        this.userId = UserContextHolder.getUserId();
    }


}
