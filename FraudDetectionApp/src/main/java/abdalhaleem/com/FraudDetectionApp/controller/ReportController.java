package abdalhaleem.com.FraudDetectionApp.controller;

import abdalhaleem.com.FraudDetectionApp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    public ReportController (ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("reports/transactions")
    public Map<String, Object> transactionReport(@RequestParam(value = "from_date", required = false) String fromDate, @RequestParam(value = "to_date", required = false) String toDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalTransactions", reportService.getTotal(fromDate, toDate));
        map.put("successTransactions", reportService.getSuccess(fromDate, toDate));
        map.put("fraudTransactions", reportService.getFraud(fromDate, toDate));
        map.put("fraudRate", String.valueOf(reportService.getFraudRate(fromDate, toDate)) + '%');

        return map;
    }

    @GetMapping("reports/alerts")
    public Map<String, Object> alertReport(@RequestParam(value = "from_date", required = false) String fromDate, @RequestParam(value = "to_date", required = false) String toDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalAlerts", reportService.getTotalAlerts(fromDate, toDate));
        map.put("resolvedAlerts", reportService.getResolvedAlerts(fromDate, toDate));
        map.put("pendingAlerts", reportService.getPendingAlerts(fromDate, toDate));
        return map;
    }

    @GetMapping("reports/country")
    public List<Map<String, Object>> countryReport(@RequestParam(value = "from_date", required = false) String fromDate, @RequestParam(value = "to_date", required = false) String toDate) {

        return reportService.getFraudCountry(fromDate, toDate);
    }

    @GetMapping("reports/lossAmount")
    public Map<String, Object> lossReport(@RequestParam(value = "from_date", required = false) String fromDate, @RequestParam(value = "to_date", required = false) String toDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalLossAmount", reportService.getLossAmt(fromDate, toDate));
        return map;
    }
}
