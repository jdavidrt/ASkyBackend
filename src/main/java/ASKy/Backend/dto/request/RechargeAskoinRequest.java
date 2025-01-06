package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RechargeAskoinRequest {
    @NotNull
    private Integer userId;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotBlank
    private String method;

}
