package cs309.backend.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInfoData(
    @NotNull @NotBlank int id,
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String displayName
) { }
