package ASKy.Backend.dto.response;

import lombok.Data;

@Data
public class ExpertResponse {
    private Integer id;
    private String fullName;
    private Double basePrice;
    private Double rating;
    private Integer consultationsCompleted;
    private String specialties;
    private String description;
}