package ASKy.Backend.repository;

import ASKy.Backend.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExpertRepository extends JpaRepository<Expert, Integer>, JpaSpecificationExecutor<Expert> {
    List<Expert> findBySanctionedTrue();
}