package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateTopicRequest {
    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    private String description;

    private Integer subjectId;
}
