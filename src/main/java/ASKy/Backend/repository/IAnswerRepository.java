package ASKy.Backend.repository;

import ASKy.Backend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Integer>, JpaSpecificationExecutor<Answer> {
    List<Answer> findByQuestionQuestionId(Integer questionId);
    List<Answer> findByBodyContaining(String keyword);
    long countByQuestionExpertUserIdAndType(Integer expertId, Byte type);
}
