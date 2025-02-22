package ASKy.Backend.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RejectQuestionRequest {
    @NotNull
    private Integer questionId;

    @NotBlank
    private String rejectionReason; // 🔹 Justification for rejection
}
