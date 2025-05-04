package abdalhaleem.com.FraudDetectionApp.repo;

import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepo extends JpaRepository <ParametersModel, Integer> {

}
