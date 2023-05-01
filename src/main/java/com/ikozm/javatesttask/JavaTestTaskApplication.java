package com.ikozm.javatesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTestTaskApplication.class, args);
    }
}
