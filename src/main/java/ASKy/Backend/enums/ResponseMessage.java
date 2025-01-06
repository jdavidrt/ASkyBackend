package ASKy.Backend.enums;

public enum ResponseMessage {
    USER_CREATED_SUCCESS("Usuario creado exitosamente"),
    USER_UPDATED_SUCCESS("Usuario actualizado exitosamente"),
    USER_DELETED_SUCCESS("Usuario eliminado exitosamente"),
    USERS_RETRIEVED_SUCCESS("Usuarios obtenidos exitosamente"),
    USER_NOT_FOUND("Usuario no encontrado"),
    VALIDATION_ERROR("Error de validación");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}