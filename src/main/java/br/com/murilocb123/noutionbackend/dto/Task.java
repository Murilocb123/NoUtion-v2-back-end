package br.com.murilocb123.noutionbackend.dto;

import br.com.murilocb123.noutionbackend.enums.StatusEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Task(
        UUID id,
        String title,
        String content,
        StatusEntity status,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
