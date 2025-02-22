package ASKy.Backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionResponse {
    private Integer id;
    private String title;
    private String body;
    private Integer price;
    private Integer topicId;
    private LocalDateTime deadline;
    private String imageUrl;
}
