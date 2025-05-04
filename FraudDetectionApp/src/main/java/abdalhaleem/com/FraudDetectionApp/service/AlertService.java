package abdalhaleem.com.FraudDetectionApp.service;

import abdalhaleem.com.FraudDetectionApp.model.AlertModel;
import abdalhaleem.com.FraudDetectionApp.repo.AlertRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlertService {

    private final AlertRepo alertRepo;

    public AlertService(AlertRepo alertRepo) {
        this.alertRepo = alertRepo;
    }

    public void addAlert(AlertModel alert) {
        alertRepo.save(alert);
    }

    public void resolveAlert(String transactionId, String addInfo) throws NoSuchElementException, IllegalArgumentException {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID cannot be blank");
        } else if (addInfo.length() > 255) {
            throw new IllegalArgumentException("Additional info must be less than 255 characters");
        } else {
            AlertModel alert = alertRepo.findById(transactionId).orElseThrow(() -> new NoSuchElementException("Alert with transaction ID " + transactionId + " not found"));
            alert.setResolved(true);
            alert.setAdditionalInfo(addInfo);
            alert.setResolvedAt(Instant.now());
            alertRepo.save(alert);
        }
    }

    public String deleteAlert(String transactionId) {
        AlertModel alert = getAlert(transactionId);
        alertRepo.deleteById(transactionId);
        return "Alert has been deleted successfully";
    }

    public AlertModel getAlert(String transactionId) throws NoSuchElementException {
        return alertRepo.findById(transactionId).orElseThrow(() -> new NoSuchElementException("Alert with transaction ID " + transactionId + " not found"));
    }

    public List<AlertModel> getAllAlerts() {
        return alertRepo.findAll();

    }
}
