package br.com.murilocb123.noutionbackend.infra.service.impl;

import br.com.murilocb123.noutionbackend.infra.service.DynamoDBService;
import br.com.murilocb123.noutionbackend.infra.service.SqsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsServiceImpl implements SqsService {

    @Value("${aws.queue.createUserURL}")
    private String queueURL;

    private final SqsClient amazonSQSClient;
    private final DynamoDBService dynamoDBService;

    @Override
    public void publishNewUserMessage(UUID userId) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueURL)
                .messageGroupId("NEW_USER") // Necessário para FIFO
                .messageDeduplicationId(UUID.randomUUID().toString())
                .messageBody(userId.toString())
                .build();
        amazonSQSClient.sendMessage(request);
    }

    @Override
    @Transactional
    public void consumeNewUserMessage(Consumer<String> messageConsumer) {
        try {
            log.info("-------- Consuming messages from SQS queue: {}", queueURL);
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueURL)
                    .maxNumberOfMessages(1) // máximo 10
                    .waitTimeSeconds(5)      // Long polling
                    .build();
            var messageReceived = amazonSQSClient.receiveMessage(receiveRequest).messages();
            messageReceived.forEach(message -> {
                var content = "Body: " + message.body() + ", data de consumo: " + new Date();
                dynamoDBService.saveItem("noution-new-user", content, message.receiptHandle());
            });
            List<String> messages = messageReceived.stream()
                    .map(Message::body)
                    .toList();
            messages.forEach(messageConsumer);
            log.info("-------- Messages received from SQS: {}", messages);
            if (!messages.isEmpty()) {
                log.info("-------- Deleting messages from SQS: {}", messages);
                messageReceived.forEach(message -> {
                    DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                            .queueUrl(queueURL)
                            .receiptHandle(message.receiptHandle())
                            .build();
                    amazonSQSClient.deleteMessage(deleteRequest);
                });
            }
        } catch (Exception e) {
            log.error("Error consuming messages from SQS: {}", e.getMessage(), e);
        }
    }
}
