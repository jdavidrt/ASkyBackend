package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateTransactionRequest;
import ASKy.Backend.dto.response.URLPaypalResponse;
import ASKy.Backend.repository.ITransactionRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ASKy.Backend.model.User;
import ASKy.Backend.model.Expert;
import ASKy.Backend.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    private static final float EXCHANGE_RATE = 1.0f; // 1 ASKoin = 1000 COP
    private static final float PLATFORM_FEE = 0.10f; // 10% fee
    private static final String DEFAULT_CURRENCY = "USD";

    public Payment createPayment(Float totalAmount, String currency, String method, String description, String cancelUrl, String successUrl)
            throws PayPalRESTException {
        float netAmount = totalAmount * (1 - PLATFORM_FEE); // Deduct 10% fee
        float askoinAmount = netAmount / EXCHANGE_RATE; // Convert to ASKoins

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", totalAmount));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public PayoutBatch createExpertPayout(CreateTransactionRequest request, Integer expertId)
            throws PayPalRESTException {
        User expert = userRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario experto no encontrado"));

        // Verificar que el experto tiene suficientes ASKoins
        if (expert.getAmountAskoins() < request.getAskoinAmount()) {
            throw new IllegalArgumentException("Fondos insuficientes para realizar el retiro");
        }

        // Calcular el monto a retirar
        Float moneyAmount = request.getMoneyAmount();

        // Crear el payout de PayPal
        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();
        senderBatchHeader.setSenderBatchId(UUID.randomUUID().toString())
                .setEmailSubject("Has recibido un pago de ASKy");

        Currency amount = new Currency();
        amount.setValue(String.format(Locale.US, "%.2f", moneyAmount));
        amount.setCurrency(DEFAULT_CURRENCY); // Usamos la moneda por defecto

        PayoutItem payoutItem = new PayoutItem();
        payoutItem.setRecipientType("EMAIL")
                .setNote("Retiro de ASKoins desde plataforma ASKy")
                .setReceiver(expert.getEmail()) // Asumiendo que el email del usuario es su PayPal
                .setSenderItemId(UUID.randomUUID().toString())
                .setAmount(amount);

        List<PayoutItem> payoutItemList = new ArrayList<>();
        payoutItemList.add(payoutItem);

        Payout payout = new Payout();
        payout.setSenderBatchHeader(senderBatchHeader);
        payout.setItems(payoutItemList);

        // Ejecutar el payout
        PayoutBatch payoutBatch = payout.create(apiContext, null);

        // Registrar la transacción en la base de datos
        ASKy.Backend.model.Transaction txn = new ASKy.Backend.model.Transaction();
        txn.setType("Withdrawal");
        txn.setMoneyAmount(moneyAmount);
        txn.setAskoinAmount(request.getAskoinAmount());
        txn.setMethod(request.getMethod());
        txn.setStatus("Processing");
        txn.setUser(expert);
        txn.setDescription("Retiro a cuenta PayPal: " + payoutBatch.getBatchHeader().getPayoutBatchId());

        transactionRepository.save(txn);

        // Actualizar el saldo del experto
        expert.setAmountAskoins(expert.getAmountAskoins() - request.getAskoinAmount());
        userRepository.save(expert);

        return payoutBatch;
    }

    // Método para verificar el estado de un payout
    public PayoutBatch getPayoutStatus(String payoutBatchId) throws PayPalRESTException {
        return Payout.get(apiContext, payoutBatchId);
    }

    // Método para procesar el retiro completo y devolver la respuesta apropiada
    public URLPaypalResponse processExpertPayout(CreateTransactionRequest request, Integer expertId) {
        try {
            PayoutBatch payoutBatch = createExpertPayout(request, expertId);
            String payoutId = payoutBatch.getBatchHeader().getPayoutBatchId();
            String status = payoutBatch.getBatchHeader().getBatchStatus();

            // Aquí podrías devolver una URL a un panel donde el experto puede ver el estado de su retiro
            // O simplemente devolver un mensaje con el estado
            return new URLPaypalResponse("Retiro procesado correctamente. ID: " + payoutId + ", Estado: " + status);

        } catch (PayPalRESTException e) {
            throw new RuntimeException("Error al procesar el pago con PayPal: " + e.getMessage(), e);
        }
    }
}
