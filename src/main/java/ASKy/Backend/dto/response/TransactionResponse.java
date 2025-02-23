package ASKy.Backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Integer id;
    private String type;
    private Float moneyAmount;
    private String description;
    private Float askoinAmount;
//    private Integer questionsId;
    private String method;
    private String status; // "Completed" or "Pending"
    private LocalDateTime createdAt;

}
