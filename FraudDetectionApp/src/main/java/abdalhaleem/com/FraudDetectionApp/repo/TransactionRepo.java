package abdalhaleem.com.FraudDetectionApp.repo;

import abdalhaleem.com.FraudDetectionApp.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionModel,String> {

}
