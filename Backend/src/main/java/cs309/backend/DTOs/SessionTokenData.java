package cs309.backend.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SessionTokenData(
    @NotNull @NotBlank boolean success,
    @NotNull String sessionJwt
) {
    public static final SessionTokenData FAILED_LOGIN = new SessionTokenData(false, "");
}
