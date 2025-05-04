package abdalhaleem.com.FraudDetectionApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AlertModel {

    @Id
    private String transactionId;
    private double fraudScore;
    @Column(length = 1000)
    private String alertMessage;
    private boolean isResolved;
    private Instant resolvedAt;
    private Instant createdAt;
    private String additionalInfo;

    @Override
    public String toString() {
        return "AlertModel{" + ", transactionId='" + transactionId + '\'' + ", fraudScore=" + fraudScore + ", alertMessage='" + alertMessage + '\'' + ", isResolved=" + isResolved + ", resolvedAt=" + resolvedAt + ", createdAt=" + createdAt + ", additionalInfo='" + additionalInfo + '\'' + '}';
    }
}
