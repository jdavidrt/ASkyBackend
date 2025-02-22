package ASKy.Backend.repository;

import ASKy.Backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // Cambiar "findByUserId" a "findByUserUserId" para que coincida con la relación
    List<Question> findByUserUserId(Integer userId);
    List<Question> findAllByUserUserId(Integer userId);

    // Buscar preguntas por título o cuerpo
    List<Question> findByTitleContainingOrBodyContaining(String titleKeyword, String bodyKeyword);
}

