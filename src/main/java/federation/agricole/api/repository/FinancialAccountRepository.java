package federation.agricole.api.repository;


import federation.agricole.api.entity.AccountTypeEnum;
import federation.agricole.api.entity.BankEnum;
import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.entity.MobileBankingServiceEnum;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    """
                    SELECT id, collectivity_id, account_type, amount, holder_name,
                           bank_name, bank_code, bank_branch_code, bank_account_number,
                           bank_account_key, mobile_banking_service, mobile_number
                    FROM financial_account WHERE id = ?
                    """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsCashAccountForCollectivity(String collectivityId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM financial_account WHERE collectivity_id = ? AND account_type = 'CASH'");
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creditAccount(String accountId, Double amount) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE financial_account SET amount = amount + ? WHERE id = ?");
            ps.setDouble(1, amount);
            ps.setString(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FinancialAccount mapRow(ResultSet rs) throws SQLException {
        FinancialAccount account = new FinancialAccount();
        account.setId(rs.getString("id"));
        account.setCollectivityId(rs.getString("collectivity_id"));
        account.setAccountType(AccountTypeEnum.valueOf(rs.getString("account_type")));
        account.setAmount(rs.getDouble("amount"));
        account.setHolderName(rs.getString("holder_name"));
        String bankName = rs.getString("bank_name");
        if (bankName != null) account.setBankName(BankEnum.valueOf(bankName));
        account.setBankCode(rs.getObject("bank_code") != null ? rs.getInt("bank_code") : null);
        account.setBankBranchCode(rs.getObject("bank_branch_code") != null ? rs.getInt("bank_branch_code") : null);
        account.setBankAccountNumber(rs.getObject("bank_account_number") != null ? rs.getInt("bank_account_number") : null);
        account.setBankAccountKey(rs.getObject("bank_account_key") != null ? rs.getInt("bank_account_key") : null);
        String mobileService = rs.getString("mobile_banking_service");
        if (mobileService != null) account.setMobileBankingService(MobileBankingServiceEnum.valueOf(mobileService));
        account.setMobileNumber(rs.getString("mobile_number"));
        return account;
    }
}
