package ASKy.Backend.controller;


import ASKy.Backend.dto.request.CreateTransactionRequest;
import ASKy.Backend.dto.response.TransactionResponse;
import ASKy.Backend.service.PaypalService;
import ASKy.Backend.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(
            summary = "Recargar ASKoins",
            description = "Permite a un usuario recargar ASKoins a su cuenta mediante PayPal.",
            requestBody = @RequestBody(
                    description = "Datos de la transacción de recarga.",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateTransactionRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recarga procesada con éxito.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
            }
    )
    @PostMapping("/recharge")
    public ResponseEntity<TransactionResponse> recharge(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        return ResponseEntity.ok(transactionService.processRecharge(request, userId));
    }

    @Operation(
            summary = "Retirar ASKoins",
            description = "Permite a un usuario retirar ASKoins de su cuenta a través de PayPal.",
            requestBody = @RequestBody(
                    description = "Datos de la transacción de retiro.",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateTransactionRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retiro procesado con éxito.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
            }
    )
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        return ResponseEntity.ok(transactionService.processWithdrawal(request, userId));
    }
}