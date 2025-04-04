package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KafkaProducerService;

import lombok.Data;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;
    
    @Value("${kafka.topic.example:example-topic}")
    private String topic;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody MessageRequest request) {
        kafkaProducerService.sendMessage(topic, request.getMessage());
        return ResponseEntity.ok("消息已发送到主题: " + topic);
    }
    
    @PostMapping("/publish-with-key")
    public ResponseEntity<String> publishWithKey(@RequestBody KeyedMessageRequest request) {
        kafkaProducerService.sendMessageWithKey(topic, request.getKey(), request.getMessage());
        return ResponseEntity.ok("带key的消息已发送到主题: " + topic);
    }
    
    @Data
    public static class MessageRequest {
        private String message;
    }
    
    @Data
    public static class KeyedMessageRequest {
        private String key;
        private String message;
    }
} 