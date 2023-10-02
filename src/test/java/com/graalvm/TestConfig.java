package com.graalvm;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public abstract class TestConfig {

    // https://java.testcontainers.org/test_framework_integration/manual_lifecycle_control/
    public static final MySQLContainer<?> container;

    static {
        // Build MySQL container
        container = new MySQLContainer<>("mysql:latest")
                .withDatabaseName("custom_db")
                .withUsername("password")
                .withPassword("password");

        // Start container
        container.start();
    }

}
