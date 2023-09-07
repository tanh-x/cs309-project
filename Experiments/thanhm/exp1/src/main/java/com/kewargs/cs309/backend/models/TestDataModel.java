package com.kewargs.cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestDataModel {
    @NotNull
    @NotBlank
    private String message;

    private String[] optional;
}
