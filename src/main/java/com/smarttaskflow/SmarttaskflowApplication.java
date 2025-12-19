package com.smarttaskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smarttaskflow.repository")
public class SmarttaskflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmarttaskflowApplication.class, args);
    }
}