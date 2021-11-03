package com.springboot.thymeleaf.pagination.v2.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class BeanConfiguration {

    @Bean
    public Faker faker() {
        return new Faker(new Locale("en-US"));
    }
}
