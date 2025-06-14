package br.com.murilocb123.noutionbackend.entities;

import br.com.murilocb123.noutionbackend.enums.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
public class TaskEntity extends AbstractEntity {

    @Column
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEntity status;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "task"))
    @Column(name = "tag")
    private List<String> tags;

}
