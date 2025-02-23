package ASKy.Backend.repository;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByExpert(Expert expert);
}