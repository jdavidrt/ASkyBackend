package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAnswerRequest {
    @NotBlank
    private String body;

    @NotNull
    private Integer questionId;

    @NotNull
    private Integer expertId; //  Track which expert provided the answer

    @NotNull
    private Integer userId; //  Track which user received the answer

}
