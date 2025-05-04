package abdalhaleem.com.FraudDetectionApp.service;

import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import abdalhaleem.com.FraudDetectionApp.repo.ParametersRepo;
import org.springframework.stereotype.Service;

@Service
public class ParametersService {

    private final ParametersRepo parametersRepo;

    public ParametersService(ParametersRepo parametersRepo){
        this.parametersRepo = parametersRepo;
    }

    public void updateParameters(ParametersModel parameters) {
        parametersRepo.save(parameters);
    }

    public void deleteParameters() {
        parametersRepo.deleteAll();
    }

    public ParametersModel getParameters() {
        return parametersRepo.findById(1).orElse(new ParametersModel());
    }
}
