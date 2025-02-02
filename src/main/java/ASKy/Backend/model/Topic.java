package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "topicId")
@Entity(name = "topics")
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    // 🔹 Relación con la tabla intermedia ExpertsTopics
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpertsTopics> expertTopics;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subtopics;
}