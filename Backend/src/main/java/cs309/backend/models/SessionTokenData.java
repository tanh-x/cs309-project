package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SessionTokenData(
    @NotNull @NotBlank String sessionJwt
) { }
