package com.example.gympool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GymPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymPoolApplication.class, args);
    }

}
