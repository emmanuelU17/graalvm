package com.graalvm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public abstract class TestConfig {

    // https://java.testcontainers.org/test_framework_integration/manual_lifecycle_control/
    public static final MySQLContainer<?> container;

    static {
        // Build MySQL container
        container = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("custom_db")
                .withUsername("password")
                .withPassword("password");

        // Start container
        container.start();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
