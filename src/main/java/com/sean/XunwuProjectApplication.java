package com.sean;

import com.sean.kafkademo.KafkaSender;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.sean.dao")
public class XunwuProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(XunwuProjectApplication.class, args);

//        KafkaSender sender = context.getBean(KafkaSender.class);
//
//        for (int i = 0; i < 3; i++) {
//            //调用消息发送类中的消息发送方法
//            sender.send();
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
