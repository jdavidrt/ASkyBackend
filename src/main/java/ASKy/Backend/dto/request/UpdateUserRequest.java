package ASKy.Backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    private String biography;

    private Float basePrice;

    private Boolean availability;

    @Schema(type = "string", format = "binary") // Permite manejar imágenes en Swagger UI
    private MultipartFile profileImage; // Nueva imagen de perfil (opcional)

}
