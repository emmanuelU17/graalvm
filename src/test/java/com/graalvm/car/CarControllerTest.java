package com.graalvm.car;

import com.graalvm.AbstractIntegrationTest;
import com.graalvm.car.dto.CarDTO;
import com.graalvm.car.dto.NestedDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest extends AbstractIntegrationTest {

    private final String url = "/api/v1/car";

    @Autowired private CarService carService;
    @Autowired private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        // Persist dummy data
        NestedDTO[] arr = {
                new NestedDTO("key 1", UUID.randomUUID().toString()),
                new NestedDTO("key 2", UUID.randomUUID().toString())
        };

        for (int i = 0; i < 10; i++) {
            var dto = new CarDTO("Car " + (i + 1), arr);
            this.carService.create(dto);
        }
    }

    @AfterEach
    void tearDown() {
        this.carRepository.deleteAll();
    }

    @Test
    void all() throws Exception {
        this.MOCKMVC
                .perform(get(url).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void create() throws Exception {
        // payload
        var dto = new CarDTO(
                "BMW",
                new NestedDTO[]{
                        new NestedDTO("key 3", "mat-black"),
                        new NestedDTO("key 4", "red")
                }
        );

        this.MOCKMVC
                .perform(post(url)
                        .content(this.MAPPER.writeValueAsString(dto))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createImage() throws Exception {
        // payload
        var dto = new CarDTO(
                "Nissan",
                new NestedDTO[] {
                        new NestedDTO("key 5", "mat-black"),
                        new NestedDTO("key 6", "blue")
                }
        );

        MockMultipartFile[] files = getFiles();

        this.MOCKMVC
                .perform(multipart(url + "/image")
                        .file(files[0])
                        .file(files[1])
                        .content(this.MAPPER.writeValueAsString(dto))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @NotNull
    private static MockMultipartFile[] getFiles() {
        return new MockMultipartFile[]{
                new MockMultipartFile(
                        "files",
                        "uploads/image1.jpeg",
                        "image/jpeg",
                        "Test image content".getBytes()
                ),
                new MockMultipartFile(
                        "files",
                        "uploads/image2.jpeg",
                        "image/jpeg",
                        "Test image content".getBytes()
                ),
        };
    }

}