package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experts")
@PrimaryKeyJoinColumn(name = "user_id")
public class Expert extends User{

    @Lob
    @Column(name = "biography")
    private String biography;

    @Column(name = "average_rating", nullable = false)
    private Float averageRating = 0.0f;

    @Column(name = "base_price", nullable = false)
    private Float basePrice;

    @Column(name = "availability", nullable = false)
    private Boolean availability = true;

    @Column(name = "response_rate", nullable = false)
    private Float responseRate = 0.0f;

    @Column(name = "total_responses", nullable = false)
    private Integer totalResponses = 0;

    // 🔹 Relación con la tabla intermedia ExpertsTopics
    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpertsTopics> expertTopics;
}
