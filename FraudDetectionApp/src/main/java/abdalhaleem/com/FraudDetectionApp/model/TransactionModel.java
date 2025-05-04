package abdalhaleem.com.FraudDetectionApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionModel {

    @Id
    private String transactionId;
    private String userId;
    private String cardNumber;
    private String userName;
    private BigDecimal amount;
    private String currency;
    private Instant timeStamp;
    private String location;
    private String country;
    private String merchant;
    private String transactionType;
    private String ipAddress;
    private String deviceType;
    private String browser;
    private int fraudFlag = 0; // 0 = not fraud
    private double fraudScore = 0;

    public TransactionModel(String transactionId, String userId, String cardNumber, String userName, BigDecimal amount, String currency, Instant timeStamp, String location,String country, String merchant, String transactionType, String ipAddress, String deviceType, String browser) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.amount = amount;
        this.currency = currency;
        this.timeStamp = timeStamp;
        this.location = location;
        this.country = country;
        this.merchant = merchant;
        this.transactionType = transactionType;
        this.ipAddress = ipAddress;
        this.deviceType = deviceType;
        this.browser = browser;
        this.fraudFlag = 0;
        this.fraudScore = 0;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "transactionId='" + transactionId + '\'' +
                ", userId=" + userId +
                ", cardNumber=" + cardNumber +
                ", userName='" + userName + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", timeStamp=" + timeStamp +
                ", location='" + location + '\'' +
                ", country="+ country + '\''+
                ", merchant='" + merchant + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", browser='" + browser + '\'' +
                ", fraudFlag=" + fraudFlag + '\'' +
                ", fraudScore="+ fraudScore+
                '}';
    }
}
