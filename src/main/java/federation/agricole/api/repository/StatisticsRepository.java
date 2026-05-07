package federation.agricole.api.repository;

import federation.agricole.api.dto.CollectivityInformationRest;
import federation.agricole.api.dto.CollectivityLocalStatisticsRest;
import federation.agricole.api.dto.CollectivityOverallStatisticsRest;
import federation.agricole.api.dto.MemberDescriptionRest;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepository {
    Connection connection;

    public StatisticsRepository(Connection connection) {
        this.connection = connection;
    }
    public List<CollectivityLocalStatisticsRest> getLocalStatistics(
            String collectivityId, LocalDate from, LocalDate to) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT
                        m.id,
                        m.first_name,
                        m.last_name,
                        m.email,
                        m.occupation,
    
                        COALESCE((
                            SELECT SUM(mp.amount)
                            FROM member_payment mp
                            JOIN membership_fee mf ON mp.membership_fee_id = mf.id
                            WHERE mp.member_id = m.id
                              AND mf.collectivity_id = ?
                              AND mf.status = 'ACTIVE'
                              AND mp.creation_date BETWEEN ? AND ?
                        ), 0) AS earned_amount,
    
                        GREATEST(
                            COALESCE((
                                SELECT SUM(mf2.amount)
                                FROM membership_fee mf2
                                WHERE mf2.collectivity_id = ?
                                  AND mf2.status = 'ACTIVE'
                                  AND mf2.eligible_from <= ?
                            ), 0)
                            - COALESCE((
                                SELECT SUM(mp2.amount)
                                FROM member_payment mp2
                                JOIN membership_fee mf3 ON mp2.membership_fee_id = mf3.id
                                WHERE mp2.member_id = m.id
                                  AND mf3.collectivity_id = ?
                                  AND mf3.status = 'ACTIVE'
                                  AND mp2.creation_date BETWEEN ? AND ?
                            ), 0),
                            0
                        ) AS unpaid_amount,
    
                        (
                            SELECT COUNT(*)
                            FROM collectivity_activity ca
                            WHERE ca.collectivity_id = ?
                              AND ca.executive_date BETWEEN ? AND ?
                              AND (
                                  ca.member_occupation_concerned IS NULL
                                  OR ca.member_occupation_concerned = ''
                                  OR ca.member_occupation_concerned LIKE '%' || m.occupation::text || '%'
                              )
                        ) AS required_activities,
    
                        (
                            SELECT COUNT(*)
                            FROM activity_attendance aa
                            JOIN collectivity_activity ca2 ON aa.activity_id = ca2.id
                            WHERE aa.member_id = m.id
                              AND ca2.collectivity_id = ?
                              AND aa.attendance_status = 'ATTENDED'
                              AND ca2.executive_date BETWEEN ? AND ?
                        ) AS attended_activities
    
                    FROM member m
                    JOIN collectivity_member cm ON cm.member_id = m.id
                    WHERE cm.collectivity_id = ?
                    ORDER BY m.id
                    """);

            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ps.setString(4, collectivityId);
            ps.setDate(5, Date.valueOf(to));
            ps.setString(6, collectivityId);
            ps.setDate(7, Date.valueOf(from));
            ps.setDate(8, Date.valueOf(to));
            ps.setString(9, collectivityId);
            ps.setDate(10, Date.valueOf(from));
            ps.setDate(11, Date.valueOf(to));
            ps.setString(12, collectivityId);
            ps.setDate(13, Date.valueOf(from));
            ps.setDate(14, Date.valueOf(to));
            ps.setString(15, collectivityId);

            ResultSet rs = ps.executeQuery();
            List<CollectivityLocalStatisticsRest> result = new ArrayList<>();

            while (rs.next()) {
                MemberDescriptionRest desc = new MemberDescriptionRest(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("occupation")
                );

                double earned = rs.getDouble("earned_amount");
                double unpaid = rs.getDouble("unpaid_amount");

                int required = rs.getInt("required_activities");
                int attended = rs.getInt("attended_activities");
                Double assiduity = required > 0
                        ? Math.round((attended * 100.0 / required) * 100.0) / 100.0
                        : null;

                result.add(new CollectivityLocalStatisticsRest(desc, earned, unpaid, assiduity));
            }
            return result;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<CollectivityOverallStatisticsRest> getOverallStatistics(
            LocalDate from, LocalDate to) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT
                        c.id,
                        c.name,
                        c.number,
    
                        (
                            SELECT COUNT(*)
                            FROM member m
                            WHERE m.collectivity_id = c.id
                              AND m.adhesion_date BETWEEN ? AND ?
                        ) AS new_members,
    
                        (
                            SELECT COUNT(*)
                            FROM collectivity_member cm
                            WHERE cm.collectivity_id = c.id
                        ) AS total_members,
    
                        (
                            SELECT COUNT(*)
                            FROM (
                                SELECT mp.member_id, SUM(mp.amount) AS total_paid
                                FROM member_payment mp
                                JOIN membership_fee mf ON mp.membership_fee_id = mf.id
                                WHERE mf.collectivity_id = c.id
                                  AND mf.status = 'ACTIVE'
                                  AND mp.creation_date BETWEEN ? AND ?
                                GROUP BY mp.member_id
                                HAVING SUM(mp.amount) >= COALESCE((
                                    SELECT SUM(mf2.amount)
                                    FROM membership_fee mf2
                                    WHERE mf2.collectivity_id = c.id
                                      AND mf2.status = 'ACTIVE'
                                      AND mf2.eligible_from <= ?
                                ), 0)
                            ) up_to_date
                        ) AS up_to_date_members,
    
                        (
                            SELECT COUNT(*)
                            FROM collectivity_activity ca
                            WHERE ca.collectivity_id = c.id
                              AND ca.executive_date BETWEEN ? AND ?
                        ) AS total_activities,
    
                        (
                            SELECT COUNT(*)
                            FROM activity_attendance aa
                            JOIN collectivity_activity ca2 ON aa.activity_id = ca2.id
                            WHERE ca2.collectivity_id = c.id
                              AND aa.attendance_status = 'ATTENDED'
                              AND ca2.executive_date BETWEEN ? AND ?
                        ) AS total_attended,
    
                        (
                            SELECT COUNT(*)
                            FROM collectivity_activity ca3
                            JOIN collectivity_member cm2 ON cm2.collectivity_id = c.id
                            WHERE ca3.collectivity_id = c.id
                              AND ca3.executive_date BETWEEN ? AND ?
                              AND (
                                  ca3.member_occupation_concerned IS NULL
                                  OR ca3.member_occupation_concerned = ''
                                  OR EXISTS (
                                      SELECT 1 FROM member m2
                                      WHERE m2.id = cm2.member_id
                                        AND ca3.member_occupation_concerned
                                            LIKE '%' || m2.occupation::text || '%'
                                  )
                              )
                        ) AS total_required
    
                    FROM collectivity c
                    ORDER BY c.number
                    """);

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ps.setDate(5, Date.valueOf(to));
            ps.setDate(6, Date.valueOf(from));
            ps.setDate(7, Date.valueOf(to));
            ps.setDate(8, Date.valueOf(from));
            ps.setDate(9, Date.valueOf(to));
            ps.setDate(10, Date.valueOf(from));
            ps.setDate(11, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            List<CollectivityOverallStatisticsRest> result = new ArrayList<>();

            while (rs.next()) {
                CollectivityInformationRest info = new CollectivityInformationRest(
                        rs.getString("name"),
                        rs.getObject("number") != null ? rs.getInt("number") : null
                );

                int newMembers    = rs.getInt("new_members");
                int totalMembers  = rs.getInt("total_members");
                int upToDate      = rs.getInt("up_to_date_members");
                int totalAct      = rs.getInt("total_activities");
                int totalAttended = rs.getInt("total_attended");
                int totalRequired = rs.getInt("total_required");

                double currentDuePct = totalMembers > 0
                        ? Math.round((upToDate * 100.0 / totalMembers) * 100.0) / 100.0
                        : 0.0;

                Double assiduityPct = totalRequired > 0
                        ? Math.round((totalAttended * 100.0 / totalRequired) * 100.0) / 100.0
                        : null;

                result.add(new CollectivityOverallStatisticsRest(
                        info, newMembers, currentDuePct, assiduityPct));
            }
            return result;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}