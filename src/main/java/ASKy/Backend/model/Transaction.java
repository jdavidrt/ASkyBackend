package ASKy.Backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "transactionId")
@Entity(name = "transactions")
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

//    @Column(name = "amount", nullable = false)
//    private Float amount;

    @Column(name = "askoin_amount")
    private Float askoinAmount; // Amount in ASKoins

    @Column(name = "money_amount")
    private Float moneyAmount; // Money amount in COP

    @Column(name = "method", length = 45)
    private String method;  // "PayPal"

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // "Pending" or "Completed"


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}