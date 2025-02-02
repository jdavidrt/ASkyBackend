package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "subjectId")
@Entity(name = "subjects")
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    // 🔹 Cada `Subject` pertenece solo a un `Topic`
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}