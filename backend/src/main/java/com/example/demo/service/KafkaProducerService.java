package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("发送消息到主题 {} 成功，消息: {}", topic, message);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("发送消息到主题 {} 失败，消息: {}", topic, message, ex);
            }
        });
    }

    public void sendMessageWithKey(String topic, String key, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("发送消息到主题 {} 成功，key: {}, 消息: {}", topic, key, message);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("发送消息到主题 {} 失败，key: {}, 消息: {}", topic, key, message, ex);
            }
        });
    }
} 