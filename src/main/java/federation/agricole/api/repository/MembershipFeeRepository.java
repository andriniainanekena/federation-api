package federation.agricole.api.repository;

import federation.agricole.api.entity.ActivityStatusEnum;
import federation.agricole.api.entity.FrequencyEnum;
import federation.agricole.api.entity.MembershipFee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MembershipFeeRepository {
    Connection connection;

    public MembershipFeeRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<MembershipFee> findById(String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id, collectivity_id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MembershipFee> findAllByCollectivityId(String collectivityId) {
        List<MembershipFee> fees = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id, collectivity_id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE collectivity_id = ?");
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) fees.add(mapRow(rs));
            return fees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MembershipFee save(MembershipFee fee) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status)
                    VALUES (?, ?, ?, ?::frequency_enum, ?, ?, ?::activity_status_enum)
                    """);
            ps.setString(1, fee.getId());
            ps.setString(2, fee.getCollectivityId());
            ps.setDate(3, Date.valueOf(fee.getEligibleFrom()));
            ps.setString(4, fee.getFrequency().name());
            ps.setDouble(5, fee.getAmount());
            ps.setString(6, fee.getLabel());
            ps.setString(7, fee.getStatus().name());
            ps.executeUpdate();
            return fee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MembershipFee mapRow(ResultSet rs) throws SQLException {
        MembershipFee fee = new MembershipFee();
        fee.setId(rs.getString("id"));
        fee.setCollectivityId(rs.getString("collectivity_id"));
        fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
        fee.setFrequency(FrequencyEnum.valueOf(rs.getString("frequency")));
        fee.setAmount(rs.getDouble("amount"));
        fee.setLabel(rs.getString("label"));
        fee.setStatus(ActivityStatusEnum.valueOf(rs.getString("status")));
        return fee;
    }
}
