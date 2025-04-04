package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/table")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:7000", "http://www.lhzzzzz.cn", "http://156.238.240.41"})
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private KafkaProducerService kafkaProducerService;
    
    @Value("${kafka.topic.example:table-data-topic}")
    private String topic;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        // 发送用户数据查询通知到Kafka
        sendKafkaMessage("USER_QUERY", "查询到 " + users.size() + " 条用户记录", null);
        return users;
    }
    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        // 发送用户创建通知到Kafka
        sendKafkaMessage("USER_CREATE", "新用户已创建", savedUser);
        return savedUser;
    }
    
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        // 发送用户更新通知到Kafka
        sendKafkaMessage("USER_UPDATE", "用户已更新", updatedUser);
        return updatedUser;
    }
    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        // 发送用户删除通知到Kafka
        Map<String, Object> data = new HashMap<>();
        data.put("userId", id);
        sendKafkaMessage("USER_DELETE", "用户已删除", data);
    }
    
    /**
     * 向Kafka发送结构化的JSON消息
     */
    private void sendKafkaMessage(String action, String message, Object data) {
        try {
            Map<String, Object> kafkaMessage = new HashMap<>();
            kafkaMessage.put("action", action);
            kafkaMessage.put("message", message);
            kafkaMessage.put("timestamp", System.currentTimeMillis());
            if (data != null) {
                kafkaMessage.put("data", data);
            }
            
            String jsonMessage = objectMapper.writeValueAsString(kafkaMessage);
            kafkaProducerService.sendMessage(topic, jsonMessage);
        } catch (JsonProcessingException e) {
            // 如果JSON序列化失败，发送简单文本消息
            kafkaProducerService.sendMessage(topic, action + ": " + message);
        }
    }
} 