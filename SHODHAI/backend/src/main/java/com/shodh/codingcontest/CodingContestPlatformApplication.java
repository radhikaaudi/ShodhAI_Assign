package com.shodh.codingcontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodingContestPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingContestPlatformApplication.class, args);
    }

}

