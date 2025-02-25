package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "answerId")
@Entity(name = "answers")
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;

    @Column(name = "type", nullable = false)
    private Byte type = 0;  // 0 = Rejected, 1 = Accepted

    @Lob
    @Column(name = "body", nullable = false)
    private String body; // Can be an answer or a rejection reason

    @OneToOne
    @JoinColumn(name = "question_id", nullable = false, unique = true)
    private Question question; // Each question has exactly one answer

//    @OneToOne(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Rating rating; // One-to-One relationship with rating

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
