package com.graalvm.car;

import com.graalvm.car.dto.CarDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(path = "api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    List<Car> all() {
        return this.carService.all();
    }

    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    void create(@Valid @RequestBody CarDTO dto) {
        assert dto.brand() != null;
        assert dto.nestedDTOS().length > 0;
        carService.create(dto);
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/image", consumes = MULTIPART_FORM_DATA_VALUE)
    void createWithImage(
            @Valid @RequestPart CarDTO dto,
            @RequestParam(value = "files") MultipartFile[] files
    ) {
        // To test REST semantics
        assert dto.brand() != null;
        assert dto.nestedDTOS().length > 0;
        assert files.length > 0;
        log.info("Created a car with images :)");
        log.info("DTO {}", dto);
    }

}
