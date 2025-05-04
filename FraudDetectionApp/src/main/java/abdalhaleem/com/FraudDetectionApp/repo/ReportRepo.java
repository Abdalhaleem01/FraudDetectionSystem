package abdalhaleem.com.FraudDetectionApp.repo;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ReportRepo {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReportRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int findTotal(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM transaction_model WHERE DATE_TRUNC('day', time_stamp) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public int findSuccess(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM transaction_model WHERE DATE_TRUNC('day', time_stamp) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND fraud_flag = 0";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public int findFraud(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM transaction_model WHERE DATE_TRUNC('day', time_stamp) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND fraud_flag = 1";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public int findTotalAlerts(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM alert_model WHERE DATE_TRUNC('day', created_at) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public int findResolvedAlerts(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM alert_model WHERE DATE_TRUNC('day', created_at) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) " + "AND IS_RESOLVED = TRUE";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public int findPendingAlerts(String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM alert_model WHERE DATE_TRUNC('day', created_at) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', created_at)) " + "AND IS_RESOLVED = FALSE";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, params, Integer.class));
    }

    public List<Map<String, Object>> findFraudCountry(String fromDate, String toDate) {
        String sql = "SELECT COUNTRY,COUNT(*) AS FRAUD_COUNT FROM transaction_model WHERE DATE_TRUNC('day', time_stamp) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND FRAUD_FLAG = 1 " + "GROUP BY COUNTRY " + "ORDER BY FRAUD_COUNT DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return jdbcTemplate.queryForList(sql, params);
    }

    public Integer findLossAmt(String fromDate, String toDate) {
        String sql = "SELECT SUM(AMOUNT) FROM transaction_model WHERE DATE_TRUNC('day', time_stamp) " + "BETWEEN COALESCE(TO_DATE(:fromDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND COALESCE(TO_DATE(:toDate, 'DD/MM/YYYY'), DATE_TRUNC('day', time_stamp)) " + "AND FRAUD_FLAG = 1";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fromDate", fromDate);
        params.addValue("toDate", toDate);

        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}
