package com.ll.healthweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class HealthWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthWebApplication.class, args);
    }

}
