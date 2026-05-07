package federation.agricole.api.repository;

import federation.agricole.api.entity.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ActivityRepository {
    Connection connection;
    MemberRepository memberRepository;

    public ActivityRepository(Connection connection, MemberRepository memberRepository) {
        this.connection = connection;
        this.memberRepository = memberRepository;
    }

    public CollectivityActivity save(CollectivityActivity activity) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                """
                INSERT INTO collectivity_activity
                    (id, collectivity_id, label, activity_type, member_occupation_concerned,
                     week_ordinal, day_of_week, executive_date)
                VALUES (?, ?, ?, ?::activity_type_enum, ?, ?, ?::day_of_week_enum, ?)
                """);
            ps.setString(1, activity.getId());
            ps.setString(2, activity.getCollectivityId());
            ps.setString(3, activity.getLabel());
            ps.setString(4, activity.getActivityType().name());
            String occupations = activity.getMemberOccupationConcerned() == null ? null :
                activity.getMemberOccupationConcerned().stream()
                    .map(MemberOccupationEnum::name).collect(Collectors.joining(","));
            ps.setString(5, occupations);
            ps.setObject(6, activity.getWeekOrdinal());
            ps.setString(7, activity.getDayOfWeek() != null ? activity.getDayOfWeek().name() : null);
            ps.setDate(8, activity.getExecutiveDate() != null ? Date.valueOf(activity.getExecutiveDate()) : null);
            ps.executeUpdate();
            return activity;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<CollectivityActivity> findById(String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM collectivity_activity WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<CollectivityActivity> findAllByCollectivityId(String collectivityId) {
        List<CollectivityActivity> activities = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM collectivity_activity WHERE collectivity_id = ? ORDER BY id");
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) activities.add(mapRow(rs));
            return activities;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public ActivityMemberAttendance saveAttendance(ActivityMemberAttendance attendance) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                """
                INSERT INTO activity_attendance (id, activity_id, member_id, attendance_status)
                VALUES (?, ?, ?, ?::attendance_status_enum)
                """);
            ps.setString(1, attendance.getId());
            ps.setString(2, attendance.getActivityId());
            ps.setString(3, attendance.getMember().getId());
            ps.setString(4, attendance.getAttendanceStatus().name());
            ps.executeUpdate();
            return attendance;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<ActivityMemberAttendance> findAttendanceByActivityAndMember(String activityId, String memberId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM activity_attendance WHERE activity_id = ? AND member_id = ?");
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapAttendanceRow(rs));
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<ActivityMemberAttendance> findAttendanceByActivityId(String activityId) {
        List<ActivityMemberAttendance> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM activity_attendance WHERE activity_id = ?");
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapAttendanceRow(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public int countRequiredActivitiesForMember(String memberId, String collectivityId,
                                                 java.time.LocalDate from, java.time.LocalDate to) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                """
                SELECT COUNT(*) FROM collectivity_activity ca
                JOIN member m ON m.id = ?
                WHERE ca.collectivity_id = ?
                AND ca.executive_date BETWEEN ? AND ?
                AND (ca.member_occupation_concerned IS NULL
                     OR ca.member_occupation_concerned LIKE '%' || m.occupation::text || '%')
                """);
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public int countAttendedActivitiesForMember(String memberId, String collectivityId,
                                                  java.time.LocalDate from, java.time.LocalDate to) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                """
                SELECT COUNT(*) FROM activity_attendance aa
                JOIN collectivity_activity ca ON aa.activity_id = ca.id
                WHERE aa.member_id = ?
                AND ca.collectivity_id = ?
                AND aa.attendance_status = 'ATTENDED'
                AND ca.executive_date BETWEEN ? AND ?
                """);
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private CollectivityActivity mapRow(ResultSet rs) throws SQLException {
        CollectivityActivity a = new CollectivityActivity();
        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setLabel(rs.getString("label"));
        a.setActivityType(ActivityTypeEnum.valueOf(rs.getString("activity_type")));
        String occ = rs.getString("member_occupation_concerned");
        if (occ != null && !occ.isEmpty()) {
            a.setMemberOccupationConcerned(Arrays.stream(occ.split(","))
                .map(MemberOccupationEnum::valueOf).collect(Collectors.toList()));
        }
        a.setWeekOrdinal(rs.getObject("week_ordinal") != null ? rs.getInt("week_ordinal") : null);
        String dow = rs.getString("day_of_week");
        if (dow != null) a.setDayOfWeek(DayOfWeekEnum.valueOf(dow));
        Date execDate = rs.getDate("executive_date");
        if (execDate != null) a.setExecutiveDate(execDate.toLocalDate());
        return a;
    }

    private ActivityMemberAttendance mapAttendanceRow(ResultSet rs) throws SQLException {
        ActivityMemberAttendance att = new ActivityMemberAttendance();
        att.setId(rs.getString("id"));
        att.setActivityId(rs.getString("activity_id"));
        att.setAttendanceStatus(AttendanceStatusEnum.valueOf(rs.getString("attendance_status")));
        memberRepository.findById(rs.getString("member_id")).ifPresent(att::setMember);
        return att;
    }
}
