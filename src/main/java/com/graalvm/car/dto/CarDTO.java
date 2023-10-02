package com.graalvm.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CarDTO (
        @NotNull String brand,
        @NotNull
        @JsonProperty(value = "nested") NestedDTO[] nestedDTOS
) { }
