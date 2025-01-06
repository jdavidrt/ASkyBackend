package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAnswerRequest {
    @NotBlank
    private String body;

    @NotNull
    private Integer questionId;

}
