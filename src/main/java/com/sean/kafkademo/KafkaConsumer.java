package com.sean.kafkademo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {


//    @KafkaListener(topics = {"sean"})
    public void receive(String message) {
        log.info("++++++++++++++++KafkaMessageConsumer 接收到消息：" + message);
    }
}