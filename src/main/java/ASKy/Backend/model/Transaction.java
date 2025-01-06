package ASKy.Backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionsId;

    @Column(nullable = false, length = 45)
    private String type;

    @Column(nullable = false)
    private String amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Lob
    private String description;

    @Column(nullable = false)
    private Float askoinsAmount;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    private Integer askoinRate;

    // Getters and Setters
    public Integer getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(Integer transactionsId) {
        this.transactionsId = transactionsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAskoinsAmount() {
        return askoinsAmount;
    }

    public void setAskoinsAmount(Float askoinsAmount) {
        this.askoinsAmount = askoinsAmount;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getAskoinRate() {
        return askoinRate;
    }

    public void setAskoinRate(Integer askoinRate) {
        this.askoinRate = askoinRate;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(transactionsId, transaction.transactionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsId);
    }
}