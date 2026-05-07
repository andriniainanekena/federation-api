package federation.agricole.api.repository;

import federation.agricole.api.entity.AccountTypeEnum;
import federation.agricole.api.entity.BankEnum;
import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.entity.MobileBankingServiceEnum;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FinancialAccountRepository {
    Connection connection;

    public FinancialAccountRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<FinancialAccount> findById(String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM financial_account WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<FinancialAccount> findAllByCollectivityId(String collectivityId) {
        List<FinancialAccount> accounts = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM financial_account WHERE collectivity_id = ? ORDER BY id");
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) accounts.add(mapRow(rs));
            return accounts;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<FinancialAccount> findAllByCollectivityIdAt(String collectivityId, LocalDate at) {
        List<FinancialAccount> accounts = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT fa.id, fa.collectivity_id, fa.account_type, fa.holder_name,
                           fa.bank_name, fa.bank_code, fa.bank_branch_code, fa.bank_account_number,
                           fa.bank_account_key, fa.mobile_banking_service, fa.mobile_number,
                           (fa.amount - COALESCE((
                               SELECT SUM(ct.amount)
                               FROM collectivity_transaction ct
                               WHERE ct.account_credited_id = fa.id
                               AND ct.creation_date > ?
                           ), 0)) AS amount
                    FROM financial_account fa
                    WHERE fa.collectivity_id = ?
                    ORDER BY fa.id
                    """);
            ps.setDate(1, Date.valueOf(at));
            ps.setString(2, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) accounts.add(mapRow(rs));
            return accounts;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean existsCashAccountForCollectivity(String collectivityId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM financial_account WHERE collectivity_id=? AND account_type='CASH'");
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void creditAccount(String accountId, Double amount) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE financial_account SET amount = amount + ? WHERE id = ?");
            ps.setDouble(1, amount);
            ps.setString(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private FinancialAccount mapRow(ResultSet rs) throws SQLException {
        FinancialAccount a = new FinancialAccount();
        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setAccountType(AccountTypeEnum.valueOf(rs.getString("account_type")));
        a.setAmount(rs.getDouble("amount"));
        a.setHolderName(rs.getString("holder_name"));
        String bankName = rs.getString("bank_name");
        if (bankName != null) a.setBankName(BankEnum.valueOf(bankName));
        a.setBankCode(rs.getObject("bank_code") != null ? rs.getInt("bank_code") : null);
        a.setBankBranchCode(rs.getObject("bank_branch_code") != null ? rs.getInt("bank_branch_code") : null);
        a.setBankAccountNumber(rs.getObject("bank_account_number") != null ? rs.getInt("bank_account_number") : null);
        a.setBankAccountKey(rs.getObject("bank_account_key") != null ? rs.getInt("bank_account_key") : null);
        String mobileService = rs.getString("mobile_banking_service");
        if (mobileService != null) a.setMobileBankingService(MobileBankingServiceEnum.valueOf(mobileService));
        a.setMobileNumber(rs.getString("mobile_number"));
        return a;
    }
}
