package cs309.backend.models;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class RegistrationData {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String displayName;
}
