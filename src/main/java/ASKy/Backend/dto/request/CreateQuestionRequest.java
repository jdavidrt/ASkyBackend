package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

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

    private String image; // Opcional

}
