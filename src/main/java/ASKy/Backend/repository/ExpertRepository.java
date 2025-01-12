package ASKy.Backend.repository;

import ASKy.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM users u " +
            "WHERE u.isConsultant = true " +
            "AND (:specialty IS NULL OR u.specialties LIKE %:specialty%) " +
            "AND (:name IS NULL OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:name%) " +
            "ORDER BY CASE :orderBy " +
            "WHEN 'rating' THEN u.rating " +
            "ELSE u.rating END DESC")
    List<User> findExperts(String specialty, String name, String orderBy);
}
