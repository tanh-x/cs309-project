package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginData(
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String password
) { }
