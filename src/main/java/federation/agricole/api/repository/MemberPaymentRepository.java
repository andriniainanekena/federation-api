package federation.agricole.api.repository;

import federation.agricole.api.entity.MemberPayment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class MemberPaymentRepository {
    Connection connection;
    FinancialAccountRepository financialAccountRepository;
    MembershipFeeRepository membershipFeeRepository;

    public MemberPaymentRepository(Connection connection,
                                   FinancialAccountRepository financialAccountRepository,
                                   MembershipFeeRepository membershipFeeRepository) {
        this.connection = connection;
        this.financialAccountRepository = financialAccountRepository;
        this.membershipFeeRepository = membershipFeeRepository;
    }

    public MemberPayment save(MemberPayment payment) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    INSERT INTO member_payment
                        (id, member_id, amount, payment_mode, account_credited_id, membership_fee_id, creation_date)
                    VALUES (?, ?, ?, ?::payment_mode_enum, ?, ?, ?)
                    """);
            ps.setString(1, payment.getId());
            ps.setString(2, payment.getMemberId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMode().name());
            ps.setString(5, payment.getAccountCredited().getId());
            ps.setString(6, payment.getMembershipFee().getId());
            ps.setDate(7, Date.valueOf(payment.getCreationDate()));
            ps.executeUpdate();
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
