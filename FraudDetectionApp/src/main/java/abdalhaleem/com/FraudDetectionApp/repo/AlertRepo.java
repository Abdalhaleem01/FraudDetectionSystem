package abdalhaleem.com.FraudDetectionApp.repo;

import abdalhaleem.com.FraudDetectionApp.model.AlertModel;
import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository <AlertModel, String> {

}
