package abdalhaleem.com.FraudDetectionApp.service;

import abdalhaleem.com.FraudDetectionApp.model.TransactionModel;
import abdalhaleem.com.FraudDetectionApp.repo.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;

    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepo transactionRepo, ParametersService parametersService, AlertService alertService, FraudDetectionService fraudDetectionService) {
        this.transactionRepo = transactionRepo;
        this.fraudDetectionService = fraudDetectionService;
    }

    public void addTransaction(TransactionModel transaction) throws IllegalArgumentException {

        if (transactionRepo.findById(transaction.getTransactionId()).isPresent()) {
            throw new IllegalArgumentException("Transaction with ID " + transaction.getTransactionId() + " already exists.");

        } else {
            fraudDetectionService.fraudDetectionScore(transaction);
            transactionRepo.save(transaction);
        }
    }

    public void updateTransaction(TransactionModel transaction) throws NoSuchElementException {
        if (transactionRepo.findById(transaction.getTransactionId()).isEmpty()) {
            throw new NoSuchElementException("Transaction with ID " + transaction.getTransactionId() + " does not exist.");

        } else {
            transactionRepo.save(transaction);
        }
    }

    public void deleteTransaction(String transactionID) throws NoSuchElementException {
        if (transactionRepo.findById(transactionID).isEmpty()) {
            throw new NoSuchElementException("Transaction with ID " + transactionID + " does not exist.");

        } else {
            transactionRepo.deleteById(transactionID);
        }
    }

    public List<TransactionModel> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public TransactionModel getTransaction(String transactionId) throws NoSuchElementException {

        return transactionRepo.findById(transactionId).orElseThrow(() -> new NoSuchElementException("Transaction with ID " + transactionId + " does not exist."));
    }
}
