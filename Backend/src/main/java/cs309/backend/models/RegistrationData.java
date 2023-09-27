package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegistrationData(
    @NotNull @NotBlank String username,
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String password,
    @NotNull @NotBlank String displayName,
    @NotNull @NotBlank int privilegeLevel
) { }
