package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Transaction;
import model.User;
import repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUser(user);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}