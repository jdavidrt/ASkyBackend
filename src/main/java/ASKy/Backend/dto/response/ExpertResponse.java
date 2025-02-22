package ASKy.Backend.dto.response;

import lombok.*;

@Data
public class ExpertResponse {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String biography;
    private Float averageRating;
    private Float basePrice;
    private Boolean availability;
    private Float responseRate;
    private Boolean sanctioned;
}