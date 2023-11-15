package cs309.backend.DTOs;

import cs309.backend.jpa.entity.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserData(
    @NotNull @NotBlank int uid,
    @NotNull @NotBlank String username,
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String displayName,
    @NotNull @NotBlank boolean isVerified
) {
    public static UserData fromEntity(UserEntity ent) {
        return new UserData(
            ent.getUid(),
            ent.getUsername(),
            ent.getEmail(),
            ent.getDisplayName(),
            ent.isVerified()
        );
    }
}
