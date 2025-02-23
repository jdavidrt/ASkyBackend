package ASKy.Backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDetailResponse {
    private Integer answerId;
    private Integer questionId;
    private Integer expertId;
    private Integer userId;
    private Boolean isRight;
    private LocalDateTime createdAt;
}
