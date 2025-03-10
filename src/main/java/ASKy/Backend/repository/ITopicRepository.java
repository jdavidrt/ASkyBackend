package ASKy.Backend.repository;

import ASKy.Backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findBySubtopicsSubjectId(Integer subjectId);
    List<Topic> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);
}
