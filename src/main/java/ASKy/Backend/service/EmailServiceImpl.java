package ASKy.Backend.service;





import ASKy.Backend.dto.response.EmailResponse;
import ASKy.Backend.enums.EmailType;
import ASKy.Backend.model.EmailDetails;
import ASKy.Backend.util.TemplateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateParser templateParser;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public EmailResponse sendEmail(EmailDetails emailDetails) {
        try {
            // Si no se especifica un remitente, usar el predeterminado
            if (emailDetails.getFrom() == null || emailDetails.getFrom().isEmpty()) {
                emailDetails.setFrom(sender);
            }

            // Si no se especifica un asunto, usar el predeterminado del tipo de correo
            if (emailDetails.getSubject() == null || emailDetails.getSubject().isEmpty()) {
                emailDetails.setSubject(emailDetails.getEmailType().getSubject());
            }

            // Procesar la plantilla con las variables
            String htmlContent = templateParser.processTemplate(
                    emailDetails.getEmailType().getTemplate(),
                    emailDetails.getTemplateVariables()
            );

            // Crear y enviar el mensaje
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(emailDetails.getFrom());
            helper.setTo(emailDetails.getTo());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(htmlContent, true);

            // Agregar CC y BCC si se proporcionan
            if (emailDetails.getCc() != null && !emailDetails.getCc().isEmpty()) {
                helper.setCc(emailDetails.getCc());
            }

            if (emailDetails.getBcc() != null && !emailDetails.getBcc().isEmpty()) {
                helper.setBcc(emailDetails.getBcc());
            }

            mailSender.send(mimeMessage);
            log.info("Correo enviado con éxito a: {}", emailDetails.getTo());

            return EmailResponse.builder()
                    .success(true)
                    .message("Correo enviado con éxito")
                    .build();

        } catch (MessagingException e) {
            log.error("Error al enviar correo: {}", e.getMessage());
            return EmailResponse.builder()
                    .success(false)
                    .message("Error al enviar el correo: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public EmailResponse sendNewAnswerNotification(String to, String userName, String consultationTopic) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", userName);
        templateVariables.put("consultationTopic", consultationTopic);

        EmailDetails emailDetails = EmailDetails.builder()
                .to(to)
                .emailType(EmailType.NEW_ANSWER)
                .templateVariables(templateVariables)
                .build();

        return sendEmail(emailDetails);
    }

    @Override
    public EmailResponse sendNewConsultationNotification(String to, String expertName, String consultationTopic,
                                                         String category, String consultationId) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("expertName", expertName);
        templateVariables.put("consultationTopic", consultationTopic);
        templateVariables.put("category", category);

        templateVariables.put("consultationId", consultationId);

        EmailDetails emailDetails = EmailDetails.builder()
                .to(to)
                .emailType(EmailType.NEW_CONSULTATION)
                .templateVariables(templateVariables)
                .build();

        return sendEmail(emailDetails);
    }

    @Override
    public EmailResponse sendAskoinRechargeConfirmation(String to, String userName, int askoinAmount,
                                                        String currencySymbol, double amountPaid,
                                                        String transactionDate, String transactionId) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("userName", userName);
        templateVariables.put("askoinAmount", askoinAmount);
        templateVariables.put("currencySymbol", currencySymbol);
        templateVariables.put("amountPaid", amountPaid);
        templateVariables.put("transactionDate", transactionDate);
        templateVariables.put("transactionId", transactionId);

        EmailDetails emailDetails = EmailDetails.builder()
                .to(to)
                .emailType(EmailType.ASKOIN_RECHARGE)
                .templateVariables(templateVariables)
                .build();

        return sendEmail(emailDetails);
    }

    @Override
    public EmailResponse sendWithdrawalConfirmation(String to, String expertName, String currencySymbol,
                                                    double amountWithdrawn, String paypalEmail,
                                                    String transactionDate, String transactionId) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("expertName", expertName);
        templateVariables.put("currencySymbol", currencySymbol);
        templateVariables.put("amountWithdrawn", amountWithdrawn);
        templateVariables.put("paypalEmail", paypalEmail);
        templateVariables.put("transactionDate", transactionDate);
        templateVariables.put("transactionId", transactionId);

        EmailDetails emailDetails = EmailDetails.builder()
                .to(to)
                .emailType(EmailType.WITHDRAWAL_SUCCESS)
                .templateVariables(templateVariables)
                .build();

        return sendEmail(emailDetails);
    }
}