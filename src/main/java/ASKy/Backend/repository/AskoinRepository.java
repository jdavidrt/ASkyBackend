package ASKy.Backend.repository;

import ASKy.Backend.model.Askoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AskoinRepository extends JpaRepository<Askoin, Integer> {
    @Query("SELECT a FROM askoins a WHERE a.user.userId = :userId")
    Optional<Askoin> findByUserId(@Param("userId") Integer userId);
}
