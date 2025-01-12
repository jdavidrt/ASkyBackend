package ASKy.Backend.dto.response;


import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String errorCode;
    private String message;

    public ErrorResponse(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    // Getters y Setters
}

