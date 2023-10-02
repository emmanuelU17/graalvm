package com.graalvm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@Import(TestConfig.class)
public abstract class AbstractIntegrationTest {

    @Autowired protected MockMvc MOCKMVC;
    @Autowired protected ObjectMapper MAPPER;

    // Setup database connection
    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", TestConfig.container::getJdbcUrl);
        registry.add("spring.datasource.username", TestConfig.container::getUsername);
        registry.add("spring.datasource.password", TestConfig.container::getPassword);
    }

    @Test
    public void isDatabaseRunning() {
        Assertions.assertTrue(TestConfig.container.isRunning());
    }

}
