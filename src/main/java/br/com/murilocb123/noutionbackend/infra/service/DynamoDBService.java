package br.com.murilocb123.noutionbackend.infra.service;

import java.util.Map;

public interface DynamoDBService {
    void saveItem(String tableName, String content, String key);
    Map<String, String> listAllItems(String tableName);
}
