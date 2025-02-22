package ASKy.Backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingResponse {
    private Integer ratingId;
    private Integer userId;
    private Integer expertId;
    private Integer answerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
