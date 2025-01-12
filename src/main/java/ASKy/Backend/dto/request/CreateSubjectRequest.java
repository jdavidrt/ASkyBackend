package ASKy.Backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSubjectRequest {
    @NotBlank
    @Size(max = 45)
    private String name;

    @NotBlank
    private String description;
}

