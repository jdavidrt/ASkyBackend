package ASKy.Backend.controller;


import ASKy.Backend.dto.request.CreateTransactionRequest;
import ASKy.Backend.dto.response.TransactionResponse;
import ASKy.Backend.dto.response.URLPaypalResponse;
import ASKy.Backend.service.PaypalService;
import ASKy.Backend.service.TransactionService;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payments", description = "Controlador para la gestión de pagos y transacciones")
public class PaypalController {

    private final PaypalService paypalService;
    private final TransactionService transactionService;


    @PostMapping("/recharge")
    public ResponseEntity<TransactionResponse> recharge(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        TransactionResponse response = transactionService.processRecharge(request, userId);
        // ✅ Log para depuración (Verificar valores)
        log.info("Recarga procesada - UserID: {}, MoneyAmount: {}, AskoinAmount: {}, Method: {}",
                userId, request.getMoneyAmount(), response.getAskoinAmount(), request.getMethod());
        return ResponseEntity.ok(transactionService.processRecharge(request, userId));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        return ResponseEntity.ok(transactionService.processWithdrawal(request, userId));
    }


    @PostMapping("/expert/payout")
    public ResponseEntity<URLPaypalResponse> expertPayout( @RequestBody CreateTransactionRequest request,
                                                          @RequestParam Integer expertId) {

        log.info("Request recibido: {}", request);
        log.info("Tipo: {}, MoneyAmount: {}, AskoinAmount: {}, Method: {}",
                request.getType(), request.getMoneyAmount(),
                request.getAskoinAmount(), request.getMethod());


        try {
            // Verificar que el tipo de transacción es correcto
            if (!"Withdrawal".equals(request.getType())) {
                return ResponseEntity.badRequest()
                        .body(new URLPaypalResponse("Error: El tipo de transacción debe ser 'Withdrawal'"));
            }

            // Verificar que el método de pago es PayPal
            if (!"PayPal".equals(request.getMethod())) {
                return ResponseEntity.badRequest()
                        .body(new URLPaypalResponse("Error: Solo se admite el método de pago 'PayPal'"));
            }

            URLPaypalResponse response = paypalService.processExpertPayout(request, expertId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new URLPaypalResponse("Error: " + e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new URLPaypalResponse("Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new URLPaypalResponse("Error en el servidor: " + e.getMessage()));
        }
    }

    @GetMapping("/expert/payout/status/{payoutId}")
    public ResponseEntity<?> getPayoutStatus(@PathVariable String payoutId) {
        try {
            PayoutBatch payoutBatch = paypalService.getPayoutStatus(payoutId);
            return ResponseEntity.ok(payoutBatch.getBatchHeader().getBatchStatus());
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el estado del payout: " + e.getMessage());
        }
    }
}