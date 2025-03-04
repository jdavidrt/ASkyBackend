package ASKy.Backend.enums;

public enum EmailType {
    NEW_ANSWER("email/new-answer", "Tienes una nueva respuesta en ASKy"),
    NEW_CONSULTATION("email/new-consultation", "Nueva Consulta en ASKy - Se Requiere tu Experiencia"),
    ASKOIN_RECHARGE("email/askoin-recharge", "Recarga de Askoins Exitosa - ASKy"),
    WITHDRAWAL_SUCCESS("email/withdrawal-success", "Retiro de Fondos Exitoso - ASKy");

    private final String template;
    private final String subject;

    EmailType(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public String getSubject() {
        return subject;
    }
}