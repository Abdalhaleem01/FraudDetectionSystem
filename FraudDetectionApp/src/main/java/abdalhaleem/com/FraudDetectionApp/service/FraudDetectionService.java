package abdalhaleem.com.FraudDetectionApp.service;

import abdalhaleem.com.FraudDetectionApp.model.AlertModel;
import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import abdalhaleem.com.FraudDetectionApp.model.TransactionModel;
import abdalhaleem.com.FraudDetectionApp.repo.FraudRepo;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Service
public class FraudDetectionService {
    private final FraudRepo fraudDetectionRepo;
    private final ParametersService parametersService;
    private final AlertService alertService;
    public double fraudScore;

    String alertMessage;

    public FraudDetectionService(FraudRepo fraudDetectionRepo, ParametersService parametersService, AlertService alertService) {
        this.fraudDetectionRepo = fraudDetectionRepo;
        this.parametersService = parametersService;
        this.alertService = alertService;
    }

    public void fraudDetectionScore(TransactionModel transaction) {
        fraudScore = 0;
        alertMessage = "Transaction flagged due to the following reasons: ";

        ParametersModel parametersModel = parametersService.getParameters();

        isAmountSuspicious(transaction, parametersModel);
        isFrequencySuspicious(transaction, parametersModel);
        isCountrySuspicious(transaction, parametersModel);
        isLocationSuspicious(transaction, parametersModel);
        isUserNameSuspicious(transaction, parametersModel);
        isCurrencySuspicious(transaction, parametersModel);
        isIpAddressSuspicious(transaction, parametersModel);
        isDeviceTypeSuspicious(transaction, parametersModel);
        isBrowserSuspicious(transaction, parametersModel);
        isMerchantSuspicious(transaction, parametersModel);

        transaction.setFraudScore(fraudScore);

        if (fraudScore > parametersModel.getFraudScore()) {
            transaction.setFraudFlag(1);
            alertService.addAlert(new AlertModel(transaction.getTransactionId(), fraudScore, alertMessage, false, null, Instant.now(), " "));
        }

    }

    public boolean isAmountSuspicious(TransactionModel transaction, ParametersModel parametersModel) {
        if (transaction.getAmount().compareTo(parametersModel.getAmountThreshold()) > 0) {
            fraudScore += parametersModel.getAmtThresholdScore();
            alertMessage += "Transaction amount exceeds the allowed threshold of " + parametersModel.getAmountThreshold() + ". ";
            return true;
        }
        else return false;
    }

    public boolean isFrequencySuspicious(TransactionModel transaction, ParametersModel parametersModel) {
        if (fraudDetectionRepo.findFrequency(transaction.getCardNumber(), parametersModel.getFreqTime()) > parametersModel.getFreqCount()) {
            fraudScore += parametersModel.getFreqScore();
            alertMessage += "More than " + parametersModel.getFreqCount() + " transactions detected within the last " + parametersModel.getFreqTime() + " minutes. ";
            return true;
        }
        else  return false;

    }

    public boolean isCountrySuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getCountry(), fraudDetectionRepo.findLastCountry(transaction.getCardNumber()), parametersModel.getCountryScore(), "Transaction performed from a country different from the previous location associated with this card. ");

    }

    public boolean isLocationSuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getLocation(), fraudDetectionRepo.findLastLocation(transaction.getCardNumber()), parametersModel.getLocationScore(), "Transaction originated from an unusual location. ");
    }

    public boolean isUserNameSuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getUserName(), fraudDetectionRepo.findLastUserName(transaction.getCardNumber()), parametersModel.getUsernameScore(), "Suspicious username detected. ");

    }

    public boolean isCurrencySuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getCurrency(), fraudDetectionRepo.findLastCurrency(transaction.getCardNumber()), parametersModel.getCurrencyScore(), "Transaction involved an unusual currency. ");

    }

    public boolean isIpAddressSuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getIpAddress(), fraudDetectionRepo.findLastIPAddress(transaction.getCardNumber()), parametersModel.getIpAddressScore(), "Transaction originated from an unusual IP address. ");

    }

    public boolean isDeviceTypeSuspicious(TransactionModel transaction, ParametersModel parametersModel) {

        return isValueSuspicious(transaction.getDeviceType(), fraudDetectionRepo.findLastDeviceType(transaction.getCardNumber()), parametersModel.getDeviceTypeScore(), "Transaction performed from an unusual device. ");

    }

    public boolean isBrowserSuspicious(TransactionModel transaction, ParametersModel parametersModel) {
        return isValueSuspicious(transaction.getBrowser(), fraudDetectionRepo.findLastBrowser(transaction.getCardNumber()), parametersModel.getBrowserScore(), "Transaction originated from an unusual browser. ");
    }

    public boolean isMerchantSuspicious(TransactionModel transaction, ParametersModel parametersModel) {
        Map<String, String> susMerchantCodes = Map.of("7801", "(Online or Internet Gambling", "7802", "Horse/Dog Racing", "7995", "Gambling Transactions Betting", "6051", "Quasi Cash–Merchant", "5921", "Package Stores–Beer, Wine and Liquor", "5816", "Digital Goods: Games", "5993", "Cigar Stores and Stands", "5815", "Digital Goods: Books, Movies, Music", "5734", "Computer Software Stores", "5817", "Digital Goods: Applications (Excludes Games)");

        if (susMerchantCodes.containsKey(transaction.getMerchant())) {
            fraudScore += parametersModel.getMerchantScore();
            alertMessage += "Transaction associated with a suspicious merchant type: " + susMerchantCodes.get(transaction.getMerchant()) + ". ";
            return true;
        }
        else return false;
    }

    private boolean isValueSuspicious(String currentValue, String previousValue, double score, String message) {
        if (previousValue != null && !previousValue.isEmpty() && !Objects.equals(previousValue, currentValue)) {
            fraudScore += score;
            alertMessage += message;
            return true;
        }
        else return false;
    }
}
