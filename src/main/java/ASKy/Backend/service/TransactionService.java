package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateTransactionRequest;
import ASKy.Backend.dto.response.EmailResponse;
import ASKy.Backend.dto.response.TransactionResponse;
import ASKy.Backend.model.Transaction;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.ITransactionRepository;
import ASKy.Backend.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ASKy.Backend.service.EmailService;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;
    private  final EmailService emailService;

    private static final float EXCHANGE_RATE = 1000.0f; // 🔹 1 ASKoin = 1000 COP
    private static final float PLATFORM_FEE = 0.10f; // 🔹 10% de comisión

    public TransactionResponse processRecharge(CreateTransactionRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 🔹 Calcular el monto neto después de la comisión del 10%
        float netAmount = request.getMoneyAmount() * (1 - PLATFORM_FEE);

        // 🔹 Convertir a ASKoins (dividiendo por la tasa de cambio)
        float askoinAmount = netAmount / EXCHANGE_RATE;

        Transaction transaction = new Transaction();
        transaction.setType("Recharge");
//        transaction.setAmount(request.getMoneyAmount());
        transaction.setAskoinAmount(askoinAmount);
        transaction.setMethod(request.getMethod());
        transaction.setStatus("Completed");
        transaction.setUser(user);
        transaction.setDescription("Recarga de ASKoins");
        if(user.getAmountAskoins()== null) {
            user.setAmountAskoins(0.0f);
        }

        user.setAmountAskoins(user.getAmountAskoins() + askoinAmount);
        userRepository.save(user);
        transactionRepository.save(transaction);

        EmailResponse response = emailService.sendAskoinRechargeConfirmation(user.getEmail(),user.getFirstName(),request.getAskoinAmount().intValue(),"$" ,request.getMoneyAmount(),transaction.getCreatedAt().toString(),transaction.getTransactionId().toString());

        return new TransactionResponse(transaction.getTransactionId(), "Recharge", request.getMoneyAmount(), "Recarga exitosa",
                askoinAmount, request.getMethod(), "Completed", transaction.getCreatedAt());
    }

    public TransactionResponse processWithdrawal(CreateTransactionRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (user.getAmountAskoins() < request.getAskoinAmount()) {
            throw new IllegalArgumentException("Fondos insuficientes");
        }

        Transaction transaction = new Transaction();
        transaction.setType("Withdrawal");
        transaction.setMoneyAmount(request.getMoneyAmount());
        transaction.setAskoinAmount(request.getAskoinAmount());
        transaction.setMethod(request.getMethod());
        transaction.setStatus("Pending");
        transaction.setUser(user);
        transaction.setDescription("Retiro de ASKoins");

        transactionRepository.save(transaction);
        EmailResponse response = emailService.sendWithdrawalConfirmation(user.getEmail(),user.getFirstName(),"$" ,request.getAskoinAmount(),user.getEmail(),transaction.getCreatedAt().toString(),transaction.getTransactionId().toString());

        return new TransactionResponse(transaction.getTransactionId(), "Withdrawal", request.getMoneyAmount(), "Retiro en proceso",
                request.getAskoinAmount(), request.getMethod(), "Pending", transaction.getCreatedAt());
    }
}
