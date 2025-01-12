package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "paymentId")
@Entity(name = "payments")
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "amount", nullable = false, length = 45)
    private String amount;

    @Column(name = "method", nullable = false, length = 45)
    private String method;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}