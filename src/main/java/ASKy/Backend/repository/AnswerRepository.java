package ASKy.Backend.repository;

import ASKy.Backend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionQuestionId(Integer questionId);
    List<Answer> findByBodyContaining(String keyword);
}
