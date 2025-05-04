package abdalhaleem.com.FraudDetectionApp.controller;

import abdalhaleem.com.FraudDetectionApp.model.AlertModel;
import abdalhaleem.com.FraudDetectionApp.model.DTO.ResolveAlertRequest;
import abdalhaleem.com.FraudDetectionApp.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class AlertsController {

    private final AlertService alertService;

    public AlertsController (AlertService alertService){
        this.alertService = alertService;
    }

    @GetMapping("alerts")
    public List<AlertModel> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("alert/{transactionId}")
    public AlertModel getAlert(@PathVariable String transactionId) {
        return alertService.getAlert(transactionId);
    }

    @PostMapping("alert/resolve")
    public AlertModel resolveAlert(@Valid @RequestBody ResolveAlertRequest alert) {
        alertService.resolveAlert(alert.getTransactionId(), alert.getAdditionalInfo());
        return alertService.getAlert(alert.getTransactionId());
    }

    @DeleteMapping("alert/{transactionId}")
    public String deleteAlert(@PathVariable String transactionId) {
        return alertService.deleteAlert(transactionId);
    }

}
