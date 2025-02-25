package ASKy.Backend.repository;

import ASKy.Backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, Integer> {
    // Cambiar "findByUserId" a "findByUserUserId" para que coincida con la relación
    List<Question> findByUserUserId(Integer userId);
    List<Question> findAllByUserUserId(Integer userId);

    Optional<Question> findByQuestionIdAndExpertUserId(Integer questionId, Integer expertId);

    // Buscar preguntas por título o cuerpo
    List<Question> findByTitleContainingOrBodyContaining(String titleKeyword, String bodyKeyword);
}

