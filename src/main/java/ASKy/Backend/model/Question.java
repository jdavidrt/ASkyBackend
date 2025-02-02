package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "questionId")
@Entity(name = "questions")
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "title", nullable = false, length = 80)
    private String title;

    @Lob
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "active", nullable = false)
    private Byte active = 1;  // 1 = open, 0 = closed

    @Column(name = "status", nullable = false)
    private Byte status = 0;  // 0 = pending, 1 = accepted, 2 = rejected, 3 = canceled

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
