package com.example.personalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PersonalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalProjectApplication.class, args);
    }

}
