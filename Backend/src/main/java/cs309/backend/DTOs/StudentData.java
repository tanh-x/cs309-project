package cs309.backend.DTOs;

import cs309.backend.jpa.entity.user.StudentEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentData(
    @NotNull @NotBlank int uid,
    @NotNull @NotBlank String username,
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String displayName,
    @NotNull @NotBlank boolean isVerified,
    @Nullable Integer primaryMajor
) {
    public static StudentData fromEntity(StudentEntity ent) {
        UserEntity userEnt = ent.getUser();
        return new StudentData(
            ent.getUid(),
            userEnt.getUsername(),
            userEnt.getEmail(),
            userEnt.getDisplayName(),
            userEnt.isVerified(),
            ent.getPrimaryMajor()
        );
    }
}
