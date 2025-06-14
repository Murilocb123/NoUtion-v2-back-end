package br.com.murilocb123.noutionbackend.infra.service;

import java.util.UUID;
import java.util.function.Consumer;

public interface SqsService {
    void publishNewUserMessage(UUID userId);
    void consumeNewUserMessage(Consumer<String> messageConsumer);
}
