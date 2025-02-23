package ASKy.Backend.repository;

import ASKy.Backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserUserId(Integer userId);
    List<Transaction> findByStatus(String status);
}
