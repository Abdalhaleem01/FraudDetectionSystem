package abdalhaleem.com.FraudDetectionApp.repo;

import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import abdalhaleem.com.FraudDetectionApp.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FraudRepo extends JpaRepository <TransactionModel, Integer> {

    @Query(value = "SELECT COUNT(*) AS COUNTER FROM TRANSACTION_MODEL WHERE card_number = :cardNumber AND time_stamp >= NOW() - (:freqTime || ' MINUTE')::INTERVAL", nativeQuery = true)
    int findFrequency(@Param("cardNumber") String cardNumber, @Param("freqTime") int freqTime);

    @Query(value = "SELECT COUNTRY FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastCountry(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT LOCATION FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastLocation(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT USER_NAME FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastUserName(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT CURRENCY FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastCurrency(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT IP_ADDRESS FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastIPAddress(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT DEVICE_TYPE FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastDeviceType(@Param("cardNumber") String cardNumber);

    @Query(value = "SELECT BROWSER FROM TRANSACTION_MODEL WHERE card_number = :cardNumber ORDER BY time_stamp desc limit 1", nativeQuery = true)
    String findLastBrowser(@Param("cardNumber") String cardNumber);


}
