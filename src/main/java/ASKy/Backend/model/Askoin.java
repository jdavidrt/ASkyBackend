package ASKy.Backend.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "askoins")
public class Askoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer askoinsId;

    @Column(nullable = false)
    private String amount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "transactionId", nullable = false)
    private Transaction transaction;

    // Getters and Setters
    public Integer getAskoinsId() {
        return askoinsId;
    }

    public void setAskoinsId(Integer askoinsId) {
        this.askoinsId = askoinsId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Askoin askoin = (Askoin) o;
        return Objects.equals(askoinsId, askoin.askoinsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(askoinsId);
    }
}