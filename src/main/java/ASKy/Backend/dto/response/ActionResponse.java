package ASKy.Backend.dto.response;

import lombok.Data;

@Data
public class ActionResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public ActionResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    // Getters y Setters
}
