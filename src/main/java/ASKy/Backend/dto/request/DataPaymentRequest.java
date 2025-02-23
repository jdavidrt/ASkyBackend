package ASKy.Backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DataPaymentRequest {
    @NotBlank
    private String method;

    @NotNull
    @DecimalMin("0.0")
    private Float amount; // Amount in COP

    @NotBlank
    private String currency; // Example: "USD"

    @NotBlank
    private String description;
}