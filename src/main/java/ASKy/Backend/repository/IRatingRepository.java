package ASKy.Backend.repository;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByExpert(Expert expert);
    Optional<Rating> findByAnswerAnswerId(Integer answerId);
}