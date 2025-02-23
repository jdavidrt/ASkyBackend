package ASKy.Backend.controller;


import ASKy.Backend.dto.request.DataPaymentRequest;
import ASKy.Backend.dto.response.URLPaypalResponse;
import ASKy.Backend.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("payments")
public class PaypalController {

    private final PaypalService paypalService;
    private final String SUCCESS_URL = "http://localhost:8090/payments/success";
    private final String CANCEL_URL = "http://localhost:8090/payments/cancel";

    @Operation(summary = "Crear pago con PayPal",
            responses = {
                    @ApiResponse(responseCode = "200", description = "URL de PayPal generada exitosamente."),
                    @ApiResponse(responseCode = "400", description = "Error al procesar el pago.")
            })
    @PostMapping
    public URLPaypalResponse createPayment(@RequestBody DataPaymentRequest dataPayment) {
        log.info("Datos del pago: {}", dataPayment);
        try {
            Payment payment = paypalService.createPayment(
                    Double.valueOf(dataPayment.getAmount()),
                    dataPayment.getCurrency(),
                    dataPayment.getMethod(),
                    "sale",
                    dataPayment.getDescription(),
                    CANCEL_URL,
                    SUCCESS_URL
            );
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new URLPaypalResponse(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new URLPaypalResponse("http://localhost:3000");
    }

    @Operation(summary = "Pago exitoso")
    @GetMapping("/success")
    public RedirectView paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return new RedirectView("http://localhost:3000/payment/success");
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("http://localhost:3000");
    }

    @Operation(summary = "Pago cancelado")
    @GetMapping("/cancel")
    public RedirectView paymentCancel() {
        return new RedirectView("http://localhost:3000");
    }
}