package com.example.dosomething;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.dosomething")
public class DoSomethingBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoSomethingBackendApplication.class, args);
    }
}
