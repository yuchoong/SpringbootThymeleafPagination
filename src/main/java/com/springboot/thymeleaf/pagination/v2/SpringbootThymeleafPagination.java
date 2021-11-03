package com.springboot.thymeleaf.pagination.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Causes Lombok to generate a logger field.
@Slf4j
// Serves two purposes i.e. configuration and bootstrapping.
@SpringBootApplication
public class SpringbootThymeleafPagination {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootThymeleafPagination.class, args);
        log.info("Springboot Pagination with Thymeleaf application is started successfully .");
    }
}
