package federation.agricole.api.repository;

import federation.agricole.api.entity.CollectivityTransaction;
import federation.agricole.api.entity.PaymentModeEnum;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityTransactionRepository {
    Connection connection;
    MemberRepository memberRepository;
    FinancialAccountRepository financialAccountRepository;

    public CollectivityTransactionRepository(Connection connection,
                                              MemberRepository memberRepository,
                                              FinancialAccountRepository financialAccountRepository) {
        this.connection = connection;
        this.memberRepository = memberRepository;
        this.financialAccountRepository = financialAccountRepository;
    }

    public CollectivityTransaction save(CollectivityTransaction transaction) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    INSERT INTO collectivity_transaction
                        (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id)
                    VALUES (?, ?, ?, ?, ?::payment_mode_enum, ?, ?)
                    """);
            ps.setString(1, transaction.getId());
            ps.setString(2, transaction.getCollectivityId());
            ps.setDate(3, Date.valueOf(transaction.getCreationDate()));
            ps.setDouble(4, transaction.getAmount());
            ps.setString(5, transaction.getPaymentMode().name());
            ps.setString(6, transaction.getAccountCredited().getId());
            ps.setString(7, transaction.getMemberDebited().getId());
            ps.executeUpdate();
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CollectivityTransaction> findByCollectivityIdAndPeriod(String collectivityId,
                                                                        LocalDate from, LocalDate to) {
        List<CollectivityTransaction> transactions = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT id, collectivity_id, creation_date, amount, payment_mode,
                           account_credited_id, member_debited_id
                    FROM collectivity_transaction
                    WHERE collectivity_id = ? AND creation_date BETWEEN ? AND ?
                    ORDER BY creation_date DESC
                    """);
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityTransaction t = new CollectivityTransaction();
                t.setId(rs.getString("id"));
                t.setCollectivityId(rs.getString("collectivity_id"));
                t.setCreationDate(rs.getDate("creation_date").toLocalDate());
                t.setAmount(rs.getDouble("amount"));
                t.setPaymentMode(PaymentModeEnum.valueOf(rs.getString("payment_mode")));
                financialAccountRepository.findById(rs.getString("account_credited_id"))
                        .ifPresent(t::setAccountCredited);
                memberRepository.findById(rs.getString("member_debited_id"))
                        .ifPresent(t::setMemberDebited);
                transactions.add(t);
            }
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
