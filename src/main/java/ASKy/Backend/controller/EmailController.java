package ASKy.Backend.controller;


import ASKy.Backend.dto.request.EmailRequest;
import ASKy.Backend.dto.response.EmailResponse;
import ASKy.Backend.model.EmailDetails;
import ASKy.Backend.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest request) {
        EmailDetails details = EmailDetails.builder()
                .to(request.getTo())
                .emailType(request.getEmailType())
                .templateVariables(request.getTemplateVariables())
                .cc(request.getCc())
                .bcc(request.getBcc())
                .build();

        EmailResponse response = emailService.sendEmail(details);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/new-answer")
    public ResponseEntity<EmailResponse> sendNewAnswerNotification(
            @RequestParam String to,
            @RequestParam String userName,
            @RequestParam String consultationTopic) {

        EmailResponse response = emailService.sendNewAnswerNotification(
                to, userName, consultationTopic);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/new-consultation")
    public ResponseEntity<EmailResponse> sendNewConsultationNotification(
            @RequestParam String to,
            @RequestParam String expertName,
            @RequestParam String consultationTopic,
            @RequestParam String category,
            @RequestParam String urgency,
            @RequestParam String consultationId) {

        EmailResponse response = emailService.sendNewConsultationNotification(
                to, expertName, consultationTopic, category, consultationId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/askoin-recharge")
    public ResponseEntity<EmailResponse> sendAskoinRechargeConfirmation(
            @RequestParam String to,
            @RequestParam String userName,
            @RequestParam int askoinAmount,
            @RequestParam String currencySymbol,
            @RequestParam double amountPaid,
            @RequestParam String transactionDate,
            @RequestParam String transactionId) {

        EmailResponse response = emailService.sendAskoinRechargeConfirmation(
                to, userName, askoinAmount, currencySymbol, amountPaid,
                transactionDate, transactionId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/withdrawal-success")
    public ResponseEntity<EmailResponse> sendWithdrawalConfirmation(
            @RequestParam String to,
            @RequestParam String expertName,
            @RequestParam String currencySymbol,
            @RequestParam double amountWithdrawn,
            @RequestParam String paypalEmail,
            @RequestParam String transactionDate,
            @RequestParam String transactionId) {

        EmailResponse response = emailService.sendWithdrawalConfirmation(
                to, expertName, currencySymbol, amountWithdrawn, paypalEmail,
                transactionDate, transactionId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}