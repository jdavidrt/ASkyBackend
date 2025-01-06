package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    private String auth0Id;

    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotBlank
    @Size(max = 60)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull
    private Boolean isConsultant;

    @Size(max = 255)
    private String description; // Opcional

}
