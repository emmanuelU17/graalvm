package com.graalvm.car.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NestedDTO(@NotNull @NotEmpty String key, @NotNull @NotEmpty String value) { }
