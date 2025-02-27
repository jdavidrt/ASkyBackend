package ASKy.Backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionResponse {
    private Integer id;
    private Integer userId;
    private Integer expertId;
    private String title;
    private String body;
    private Integer price;
    private Integer topicId;
    private LocalDateTime deadline;
    private String imageUrl;
    private String active;  // 🔹 "open" or "closed"
    private String status; // 🔹 "pending", "accepted", "rejected", "canceled"
}
