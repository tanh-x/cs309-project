package cs309.backend.models;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChatMessageData (
        @NotNull @NotBlank String content,
        @NotNull @NotBlank String sender
    ) {

    }
