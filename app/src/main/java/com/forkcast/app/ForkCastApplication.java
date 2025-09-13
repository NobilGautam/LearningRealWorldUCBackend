package com.forkcast.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.forkcast")
@EntityScan(basePackages = "com.forkcast.dao.entities")
@EnableJpaRepositories(basePackages = "com.forkcast.dao.repository")
public class ForkCastApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForkCastApplication.class, args);
    }
}
