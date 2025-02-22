package ASKy.Backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class CreateQuestionRequest {
    @NotBlank
    @Size(max = 80)
    private String title;

    @NotBlank
    private String body;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    private Integer topicId;

    @NotNull
    @Future(message = "La fecha límite debe estar en el futuro")
    private LocalDateTime deadline;

    @Schema(type = "string", format = "binary") // Swagger support for file uploads
    private MultipartFile imageUrl;

}
