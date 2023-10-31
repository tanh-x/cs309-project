package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageData(
    @NotNull int sender,
    @NotNull Integer receiver,
    @NotNull @NotBlank String content
) {
}
