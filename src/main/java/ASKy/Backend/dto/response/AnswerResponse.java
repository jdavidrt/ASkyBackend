package ASKy.Backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerResponse {
    private Integer id;
    private Byte type;
    private String body;
    private Integer questionId;
    private Integer userId;
    private LocalDateTime createdAt;
    private Integer rating;
    private String comment;
    private LocalDateTime ratedAt;
    private Boolean isRight;
}