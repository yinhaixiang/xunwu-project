package com.sean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sean.dao")
public class XunwuProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(XunwuProjectApplication.class, args);
    }

}
