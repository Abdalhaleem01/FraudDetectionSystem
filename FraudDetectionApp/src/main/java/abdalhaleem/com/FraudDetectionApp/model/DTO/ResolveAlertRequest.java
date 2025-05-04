package abdalhaleem.com.FraudDetectionApp.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolveAlertRequest {

    private String transactionId;
    private String additionalInfo;

}
