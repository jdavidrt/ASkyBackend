package ASKy.Backend.controller;


import ASKy.Backend.dto.request.CreateTransactionRequest;
import ASKy.Backend.dto.request.DataPaymentRequest;
import ASKy.Backend.dto.response.TransactionResponse;
import ASKy.Backend.dto.response.URLPaypalResponse;
import ASKy.Backend.service.PaypalService;
import ASKy.Backend.service.TransactionService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;
    private final TransactionService transactionService;

    @PostMapping("/recharge")
    public ResponseEntity<TransactionResponse> recharge(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        return ResponseEntity.ok(transactionService.processRecharge(request, userId));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody CreateTransactionRequest request, @RequestParam Integer userId) {
        return ResponseEntity.ok(transactionService.processWithdrawal(request, userId));
    }
}