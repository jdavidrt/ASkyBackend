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

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "askoins_amount", nullable = false)
    private Float askoinsAmount;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "askoin_rate")
    private Integer askoinRate;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}