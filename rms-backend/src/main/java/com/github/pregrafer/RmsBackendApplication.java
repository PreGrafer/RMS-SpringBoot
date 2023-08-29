package com.github.pregrafer;

import com.github.pregrafer.Controller.SchedulingTask;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RmsBackendApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RmsBackendApplication.class, args);
    }

}
