package ASKy.Backend.repository;

import ASKy.Backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);
}
