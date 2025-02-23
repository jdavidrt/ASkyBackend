package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateTransactionRequest {
    @NotBlank
    private String type; // "Recharge" or "Withdrawal"

    @NotNull
    @DecimalMin("0.0")
    private Float moneyAmount;  // Money amount in COP

//    @NotBlank
//    private String description;

    @NotNull
    @DecimalMin("0.0")
    private Float askoinAmount; // Converted ASKoins

    @NotBlank
    private String method; // Payment method (e.g., PayPal)

    private Integer questionsId; // Opcional

}

