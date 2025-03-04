package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "ratingId")
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer ratingId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Cliente que da la calificación
    private User user;

    @ManyToOne
    @JoinColumn(name = "expert_id", nullable = false) // Experto calificado
    private Expert expert;


    @Column(name = "rating", nullable = false)
    private Integer rating; // Valor de 1 a 5 estrellas

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "answer_id", nullable = false, unique = true) // 🔹 Relate rating to the answer
    private Answer answer;

//    @OneToOne
//    @JoinColumn(name = "answer_detail_id") // Relación con la respuesta calificada
//    private AnswerDetail answerDetail;



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}