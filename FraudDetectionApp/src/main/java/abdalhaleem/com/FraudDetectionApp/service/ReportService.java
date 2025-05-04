package abdalhaleem.com.FraudDetectionApp.service;

import abdalhaleem.com.FraudDetectionApp.repo.ReportRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ReportRepo reportRepo;

    public ReportService(ReportRepo reportRepo){
        this.reportRepo = reportRepo;
    }

    public int getTotal(String fromDate, String toDate) {
        return reportRepo.findTotal(fromDate, toDate);
    }

    public int getSuccess(String fromDate, String toDate) {
        return reportRepo.findSuccess(fromDate, toDate);
    }

    public int getFraud(String fromDate, String toDate) {
        return reportRepo.findFraud(fromDate, toDate);
    }

    public int getFraudRate(String fromDate, String toDate) {
        double total = getTotal(fromDate, toDate);
        double fraud = getFraud(fromDate, toDate);
        if (total > 0) {
            return (int) Math.round((fraud / total) * 100);
        }

        return 0;
    }

    public int getTotalAlerts(String fromDate, String toDate) {
        return reportRepo.findTotalAlerts(fromDate, toDate);
    }

    public int getResolvedAlerts(String fromDate, String toDate) {
        return reportRepo.findResolvedAlerts(fromDate, toDate);
    }

    public int getPendingAlerts(String fromDate, String toDate) {
        return reportRepo.findPendingAlerts(fromDate, toDate);
    }

    public List<Map<String, Object>> getFraudCountry(String fromDate, String toDate) {
        return reportRepo.findFraudCountry(fromDate, toDate);
    }

    public Integer getLossAmt(String fromDate, String toDate) {
        return reportRepo.findLossAmt(fromDate, toDate);
    }

}
