package abdalhaleem.com.FraudDetectionApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@AllArgsConstructor
@Entity
public class ParametersModel {

    @Id
    private int id;

    private BigDecimal amountThreshold;
    private int freqTime;
    private int freqCount;
    private double amtThresholdScore;
    private double freqScore;
    private double countryScore;
    private double locationScore;
    private double usernameScore;
    private double currencyScore;
    private double ipAddressScore;
    private double deviceTypeScore;
    private double browserScore;
    private double merchantScore;
    private double fraudScore;

    public ParametersModel() {
        this.id = 1;
        this.amountThreshold = BigDecimal.valueOf(10000);
        this.freqTime = 10;
        this.freqCount = 5;
        this.amtThresholdScore = 20;
        this.freqScore = 20;
        this.countryScore = 10;
        this.locationScore = 10;
        this.usernameScore = 10;
        this.currencyScore = 10;
        this.ipAddressScore = 10;
        this.deviceTypeScore = 10;
        this.browserScore = 10;
        this.merchantScore = 10;
        this.fraudScore = 50;
    }

    @Override
    public String toString() {
        return "ParametersModel{" + "id=" + id + ", amountThreshold=" + amountThreshold + ", freqTime=" + freqTime + ", freqCount=" + freqCount + ", amtThresholdScore=" + amtThresholdScore + ", freqScore=" + freqScore + ", countryScore=" + countryScore + ", locationScore=" + locationScore + ", usernameScore=" + usernameScore + ", currencyScore=" + currencyScore + ", ipAddressScore=" + ipAddressScore + ", deviceTypeScore=" + deviceTypeScore + ", browserScore=" + browserScore + ", merchantScore=" + merchantScore + ", fraudScore=" + fraudScore + '}';
    }
}


