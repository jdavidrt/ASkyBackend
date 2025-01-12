package ASKy.Backend.enums;

public enum ResponseMessage {
    USER_CREATED_SUCCESS("Usuario creado exitosamente"),
    USER_UPDATED_SUCCESS("Usuario actualizado exitosamente"),
    USER_DELETED_SUCCESS("Usuario eliminado exitosamente"),
    USERS_RETRIEVED_SUCCESS("Usuarios obtenidos exitosamente"),
    USER_NOT_FOUND("Usuario no encontrado"),
    QUESTION_CREATED_SUCCESS("Pregunta creada exitosamente"),
    QUESTION_RETRIEVED_SUCCESS("Pregunta obtenida exitosamente"),
    QUESTIONS_RETRIEVED_SUCCESS("Preguntas obtenidas exitosamente"),
    QUESTION_ASSIGNED_SUCCESS("Pregunta asignada exitosamente"),
    QUESTION_DELETED_SUCCESS("Pregunta eliminada exitosamente"),
    TOPIC_CREATED_SUCCESS("Tema creado exitosamente"),
    TOPIC_DELETED_SUCCESS("Tema eliminado exitosamente"),
    TOPICS_RETRIEVED_SUCCESS("Temas obtenidos exitosamente"),
    SUBJECT_CREATED_SUCCESS("Subject creado exitosamente"),
    SUBJECT_DELETED_SUCCESS("Subject eliminado exitosamente"),
    SUBJECTS_RETRIEVED_SUCCESS("Subjects obtenidos exitosamente"),
    VALIDATION_ERROR("Error de validación"),
    EXPERTS_RETRIEVED_SUCCESS("Expertos obtenidos exitosamente");;


    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}