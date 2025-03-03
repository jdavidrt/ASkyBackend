package ASKy.Backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

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


    public @NotNull @DecimalMin("0.0") Float getAskoinAmount() {
        return askoinAmount;
    }

    public @NotBlank String getMethod() {
        return method;
    }


    public @NotNull @DecimalMin("0.0") Float getMoneyAmount() {
        return moneyAmount;
    }

    public @NotBlank String getType() {
        return type;
    }

    public Integer getQuestionsId() {
        return questionsId;
    }

    public void setAskoinAmount(@NotNull @DecimalMin("0.0") Float askoinAmount) {
        this.askoinAmount = askoinAmount;
    }

    public void setMethod(@NotBlank String method) {
        this.method = method;
    }

    public void setMoneyAmount(@NotNull @DecimalMin("0.0") Float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }

    public void setQuestionsId(Integer questionsId) {
        this.questionsId = questionsId;
    }



    public CreateTransactionRequest() {
    }

    public CreateTransactionRequest(String type, Float moneyAmount, Float askoinAmount, String method, Integer questionsId) {
        this.type = type;
        this.moneyAmount = moneyAmount;
        this.askoinAmount = askoinAmount;
        this.method = method;
        this.questionsId = questionsId;
    }


}

