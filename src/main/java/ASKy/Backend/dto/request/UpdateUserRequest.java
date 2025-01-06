package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(max = 60)
    private String firstName;

    @Size(max = 60)
    private String lastName;

    @Email
    private String email;

    @Size(max = 255)
    private String description;

    private Boolean isConsultant;
    private Boolean status; // Ejemplo para habilitar/deshabilitar usuarios

}
