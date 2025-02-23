package ASKy.Backend.repository;

import ASKy.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByIsConsultant(Boolean isConsultant);
    boolean existsByAuth0Id(String auth0Id);
    Optional<User> findByAuth0Id(String auth0Id);
}
