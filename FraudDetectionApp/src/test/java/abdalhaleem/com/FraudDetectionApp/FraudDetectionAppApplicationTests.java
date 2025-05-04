package abdalhaleem.com.FraudDetectionApp;

import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import abdalhaleem.com.FraudDetectionApp.model.TransactionModel;
import abdalhaleem.com.FraudDetectionApp.repo.FraudRepo;
import abdalhaleem.com.FraudDetectionApp.service.AlertService;
import abdalhaleem.com.FraudDetectionApp.service.FraudDetectionService;
import abdalhaleem.com.FraudDetectionApp.service.ParametersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // For Mockito integration
class FraudDetectionAppApplicationTests {

    @Mock
    private FraudRepo fraudRepo;

    @Mock
    private ParametersService parametersService;

    @Mock
    private AlertService alertService;

    @InjectMocks
    private FraudDetectionService fraudDetectionService;

    private TransactionModel transaction;
    private ParametersModel parameter;

    @BeforeEach
    void setUp() {
        transaction = new TransactionModel();
        transaction.setTransactionId("23423453545432");
        parameter = new ParametersModel();
    }

    @Test
    void AmountIsSuspicious() {

        transaction.setAmount(BigDecimal.valueOf(15000));
        parameter.setAmountThreshold(BigDecimal.valueOf(10000));
        assertTrue(fraudDetectionService.isAmountSuspicious(transaction, parameter));

    }

    @Test
    void AmountIsNotSuspicious() {

        transaction.setAmount(BigDecimal.valueOf(2000));
        parameter.setAmountThreshold(BigDecimal.valueOf(10000));
        assertFalse(fraudDetectionService.isAmountSuspicious(transaction, parameter));

    }

    @Test
    void AmountIsSuspiciousEdgeCase() {

        transaction.setAmount(BigDecimal.valueOf(2001));
        parameter.setAmountThreshold(BigDecimal.valueOf(2000));
        assertTrue(fraudDetectionService.isAmountSuspicious(transaction, parameter));

    }

    @Test
    void AmountIsNotSuspiciousEdgeCase() {

        transaction.setAmount(BigDecimal.valueOf(2000));
        parameter.setAmountThreshold(BigDecimal.valueOf(2000));
        assertFalse(fraudDetectionService.isAmountSuspicious(transaction, parameter));

        transaction.setAmount(BigDecimal.valueOf(1999));
        parameter.setAmountThreshold(BigDecimal.valueOf(2000));
        assertFalse(fraudDetectionService.isAmountSuspicious(transaction, parameter));

    }

    @Test
    void FrequencyIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findFrequency("5678943535435453243245", 15)).thenReturn(5);
        transaction.setCardNumber("5678943535435453243245");
        parameter.setFreqTime(15);
        parameter.setFreqCount(4);
        assertTrue(fraudDetectionService.isFrequencySuspicious(transaction, parameter));

    }

    @Test
    void FrequencyIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findFrequency("5678943535435453243645", 12)).thenReturn(10);
        transaction.setCardNumber("5678943535435453243645");
        parameter.setFreqTime(12);
        parameter.setFreqCount(15);
        assertFalse(fraudDetectionService.isFrequencySuspicious(transaction, parameter));

    }

    @Test
    void FrequencyIsSuspiciousEdgeCase() {

        // Mock repository behavior
        when(fraudRepo.findFrequency("5678943535435453243649", 10)).thenReturn(16);
        transaction.setCardNumber("5678943535435453243649");
        parameter.setFreqTime(10);
        parameter.setFreqCount(15);
        assertTrue(fraudDetectionService.isFrequencySuspicious(transaction, parameter));

    }

    @Test
    void FrequencyIsNotSuspiciousEdgeCase() {

        // Mock repository behavior
        when(fraudRepo.findFrequency("5678943535435453243649", 10)).thenReturn(15);
        transaction.setCardNumber("5678943535435453243649");
        parameter.setFreqTime(10);
        parameter.setFreqCount(15);
        assertFalse(fraudDetectionService.isFrequencySuspicious(transaction, parameter));

    }

    @Test
    void CountryIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastCountry("5678943535435493243649")).thenReturn("USA");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setCountry("Jordan");
        assertTrue(fraudDetectionService.isCountrySuspicious(transaction, parameter));

    }

    @Test
    void CountryIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastCountry("5678943535435493243649")).thenReturn("USA");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setCountry("USA");
        assertFalse(fraudDetectionService.isCountrySuspicious(transaction, parameter));

    }

    @Test
    void LocationIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastLocation("5678943535435493243649")).thenReturn("New York");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setLocation("Amman");
        assertTrue(fraudDetectionService.isLocationSuspicious(transaction, parameter));

    }

    @Test
    void LocationIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastLocation("5678943535435493243649")).thenReturn("New York");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setLocation("New York");
        assertFalse(fraudDetectionService.isLocationSuspicious(transaction, parameter));

    }

    @Test
    void UserNameIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastUserName("5678943535435493243649")).thenReturn("Adam Allan");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setUserName("John ALex");
        assertTrue(fraudDetectionService.isUserNameSuspicious(transaction, parameter));

    }

    @Test
    void UserNameIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastUserName("5678943535435493243649")).thenReturn("Adam Allan");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setUserName("Adam Allan");
        assertFalse(fraudDetectionService.isUserNameSuspicious(transaction, parameter));

    }

    @Test
    void CurrencyIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastCurrency("5678943535435493243649")).thenReturn("USD");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setCurrency("JOD");
        assertTrue(fraudDetectionService.isCurrencySuspicious(transaction, parameter));

    }

    @Test
    void CurrencyIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastCurrency("5678943535435493243649")).thenReturn("USD");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setCurrency("USD");
        assertFalse(fraudDetectionService.isCurrencySuspicious(transaction, parameter));

    }

    @Test
    void IpAddressIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastIPAddress("5678943535435493243649")).thenReturn("198.51.100.79");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setIpAddress("198.51.100.77");
        assertTrue(fraudDetectionService.isIpAddressSuspicious(transaction, parameter));

    }

    @Test
    void IpAddressIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastIPAddress("5678943535435493243649")).thenReturn("198.51.100.79");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setIpAddress("198.51.100.79");
        assertFalse(fraudDetectionService.isIpAddressSuspicious(transaction, parameter));

    }

    @Test
    void DeviceTypeIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastDeviceType("5678943535435493243649")).thenReturn("Iphone");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setDeviceType("Ipad");
        assertTrue(fraudDetectionService.isDeviceTypeSuspicious(transaction, parameter));

    }

    @Test
    void DeviceTypeIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastDeviceType("5678943535435493243649")).thenReturn("Iphone");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setDeviceType("Iphone");
        assertFalse(fraudDetectionService.isDeviceTypeSuspicious(transaction, parameter));

    }

    @Test
    void BrowserIsSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastBrowser("5678943535435493243649")).thenReturn("Chrome");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setBrowser("Brave");
        assertTrue(fraudDetectionService.isBrowserSuspicious(transaction, parameter));

    }

    @Test
    void BrowserIsNotSuspicious() {

        // Mock repository behavior
        when(fraudRepo.findLastBrowser("5678943535435493243649")).thenReturn("Chrome");
        transaction.setCardNumber("5678943535435493243649");
        transaction.setBrowser("Chrome");
        assertFalse(fraudDetectionService.isBrowserSuspicious(transaction, parameter));

    }

    @Test
    void MerchantIsSuspicious() {
        transaction.setMerchant("7801");
        assertTrue(fraudDetectionService.isMerchantSuspicious(transaction, parameter));

    }

    @Test
    void MerchantIsNotSuspicious() {
        transaction.setMerchant("7881");
        assertFalse(fraudDetectionService.isMerchantSuspicious(transaction, parameter));
    }

}
