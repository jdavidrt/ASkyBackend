package ASKy.Backend.service;


import ASKy.Backend.dto.response.EmailResponse;
import ASKy.Backend.model.EmailDetails;

public interface EmailService {

    /**
     * Envía un correo electrónico utilizando una plantilla HTML
     * @param emailDetails Detalles del correo electrónico a enviar
     * @return EmailResponse con el resultado de la operación
     */
    EmailResponse sendEmail(EmailDetails emailDetails);

    /**
     * Envía una notificación de nueva respuesta
     * @param to Destinatario del correo
     * @param userName Nombre del usuario
     * @param consultationTopic Tema de la consulta
     * @return EmailResponse con el resultado de la operación
     */
    EmailResponse sendNewAnswerNotification(String to, String userName, String consultationTopic);

    /**
     * Envía una notificación de nueva consulta a un experto
     * @param to Correo del experto
     * @param expertName Nombre del experto
     * @param consultationTopic Tema de la consulta
     * @param category Categoría de la consulta
     * @param consultationId ID de la consulta
     * @return EmailResponse con el resultado de la operación
     */
    EmailResponse sendNewConsultationNotification(String to, String expertName, String consultationTopic,
                                                  String category, String consultationId);

    /**
     * Envía una confirmación de recarga de Askoins
     * @param to Destinatario del correo
     * @param userName Nombre del usuario
     * @param askoinAmount Cantidad de Askoins recargados
     * @param currencySymbol Símbolo de la moneda utilizada
     * @param amountPaid Monto pagado
     * @param transactionDate Fecha de la transacción
     * @param transactionId ID de la transacción
     * @return EmailResponse con el resultado de la operación
     */
    EmailResponse sendAskoinRechargeConfirmation(String to, String userName, int askoinAmount,
                                                 String currencySymbol, double amountPaid,
                                                 String transactionDate, String transactionId);

    /**
     * Envía una confirmación de retiro exitoso
     * @param to Destinatario del correo
     * @param expertName Nombre del experto
     * @param currencySymbol Símbolo de la moneda
     * @param amountWithdrawn Monto retirado
     * @param paypalEmail Correo de PayPal
     * @param transactionDate Fecha de la transacción
     * @param transactionId ID de la transacción
     * @return EmailResponse con el resultado de la operación
     */
    EmailResponse sendWithdrawalConfirmation(String to, String expertName, String currencySymbol,
                                             double amountWithdrawn, String paypalEmail,
                                             String transactionDate, String transactionId);
}