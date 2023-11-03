package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectionData(
        @NotNull @NotBlank int ref,
        @NotNull @NotBlank String identifier,
        @NotNull @NotBlank int num,
        @NotNull @NotBlank String section,
        @NotNull @NotBlank int year,
        @NotNull @NotBlank int season,
        @NotNull @NotBlank boolean is_online
) {
}
