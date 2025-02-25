package ASKy.Backend.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String auth0Id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isConsultant;
    private String description;
    private Float basePrice;
    private Boolean availability;
    private Float responseRate;
    private String profileImageUrl;
    private String biography;
    private Float amountAskoins;

}
