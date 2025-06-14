package br.com.murilocb123.noutionbackend.infra.service.impl;


import br.com.murilocb123.noutionbackend.infra.service.DynamoDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamoDBServiceImpl implements DynamoDBService {

    private final DynamoDbClient amazonDynamoDbClient;

    @Override
    public void saveItem(String tableName, String content, String key) {
        log.info("Saving item to DynamoDB table: {}", tableName);
        try {
            var item = PutItemRequest.builder()
                    .item(Map.of(
                            "id", AttributeValue.fromS(key),
                            "content", AttributeValue.fromS(content)
                    ))
                    .tableName(tableName)
                    .build();
            amazonDynamoDbClient.putItem(item);
            log.info("Item saved successfully: {}", item);
        } catch (Exception e) {
            log.error("Error saving item to DynamoDB: {}", e.getMessage());
        }
    }

    @Override
    public Map<String, String> listAllItems(String tableName) {
        log.info("Listing items from DynamoDB table: {}", tableName);
        try {
            // Implement the logic to list all items from DynamoDB
            var scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .build();
            var response = amazonDynamoDbClient.scan(scanRequest);
           return response.items().stream()
                    .collect(Collectors.toMap(
                            item -> item.get("id").s(), // Assuming 'id' is the key
                            item -> item.get("content").s() // Assuming 'content' is the value
                    ));
        } catch (Exception e) {
            log.error("Error listing items from DynamoDB: {}", e.getMessage());
        }
        return Map.of();
    }
}
