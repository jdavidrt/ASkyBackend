package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateTransactionRequest {
    @NotBlank
    private String type;

    @NotNull
    @DecimalMin("0.0")
    private Float amount;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private Float askoinsAmount;

    private Integer questionsId; // Opcional

}

