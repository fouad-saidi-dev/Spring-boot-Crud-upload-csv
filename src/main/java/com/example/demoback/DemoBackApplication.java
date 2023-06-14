package com.example.demoback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application Startup Program arguments");
    }
}
