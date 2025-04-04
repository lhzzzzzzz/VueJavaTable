package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {

    /**
     * 使用@KafkaListener注解来订阅指定主题
     * topics: 指定要订阅的主题名称
     * groupId: 消费者组ID，相同组ID的消费者形成一个消费者组
     */
    @KafkaListener(topics = "${kafka.topic.example:example-topic}", groupId = "${kafka.consumer.group-id:demo-group}")
    public void listen(String message) {
        log.info("接收到消息: {}", message);
        // 在这里处理接收到的消息
        processMessage(message);
    }
    
    /**
     * 处理接收到的消息的业务逻辑
     */
    private void processMessage(String message) {
        // 根据业务需求处理消息
        log.info("处理消息: {}", message);
        // 例如：解析消息JSON、更新数据库、调用其他服务等
    }
} 