package ASKy.Backend.controller;


import ASKy.Backend.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment", description = "Controlador para la integración con PayPal")
public class PaypalController {

    private final PaypalService paypalService;

    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @Operation(summary = "Crear Pago con PayPal",
            description = "Inicia el proceso de creación de un pago en PayPal y retorna la URL de aprobación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pago creado con éxito. URL de aprobación retornada.",
                            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida o error al crear el pago.",
                            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
            })
    @PostMapping("/create")
    public String createPayment(@RequestParam Float amount, @RequestParam String description) {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";
            Payment payment = paypalService.createPayment(amount, description, cancelUrl, successUrl);
            return payment.getLinks().stream()
                    .filter(link -> link.getRel().equals("approval_url"))
                    .findFirst()
                    .map(Links::getHref)
                    .orElse("No approval URL found");
        } catch (PayPalRESTException e) {
            return "Error: " + e.getMessage();
        }
    }
}
