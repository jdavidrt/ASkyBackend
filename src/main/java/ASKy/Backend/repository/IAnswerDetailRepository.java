package ASKy.Backend.repository;
import ASKy.Backend.model.AnswerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnswerDetailRepository extends JpaRepository<AnswerDetail, Integer> {
    List<AnswerDetail> findByQuestionQuestionId(Integer questionId);
    List<AnswerDetail> findByExpertUserId(Integer expertId);
    List<AnswerDetail> findByUserUserId(Integer userId);
}
