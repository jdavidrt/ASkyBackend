package ASKy.Backend.repository;

import ASKy.Backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//    @Query("SELECT t FROM Transaction t WHERE t.user.userId = :userId")
//    List<Transaction> findByUserId(@Param("userId") Integer userId);
}
