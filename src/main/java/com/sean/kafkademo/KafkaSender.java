package com.sean.kafkademo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //发送消息方法
    public void send() {
        try {
            Message message = new Message();
            message.setId(System.currentTimeMillis());
            message.setMsg(UUID.randomUUID().toString());
            message.setSendTime(new Date());
            log.info("+++++++++++++++++++++  message = {}", objectMapper.writeValueAsString(message));
            kafkaTemplate.send("sean", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}