package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Transaction;
import model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}