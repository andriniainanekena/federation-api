package federation.agricole.api.repository;

import federation.agricole.api.entity.GenderEnum;
import federation.agricole.api.entity.Member;
import federation.agricole.api.entity.MemberOccupationEnum;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    Connection connection;

    public MemberRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Member> findById(String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id,first_name,last_name,birth_date,gender,address,profession,phone_number,email,occupation,adhesion_date,collectivity_id FROM member WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Member> findAllByCollectivityId(String collectivityId) {
        List<Member> members = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                           m.address, m.profession, m.phone_number, m.email,
                           cm.occupation, m.adhesion_date, m.collectivity_id
                    FROM member m
                    JOIN collectivity_member cm ON m.id = cm.member_id
                    WHERE cm.collectivity_id = ?
                    ORDER BY m.id
                    """);
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) members.add(mapRow(rs));
            return members;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Member> findRefereesByMemberId(String memberId) {
        List<Member> referees = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    SELECT m.id,m.first_name,m.last_name,m.birth_date,m.gender,m.address,
                           m.profession,m.phone_number,m.email,m.occupation,m.adhesion_date,m.collectivity_id
                    FROM member m JOIN member_referees mr ON m.id=mr.referee_id WHERE mr.member_id=?
                    """);
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) referees.add(mapRow(rs));
            return referees;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean isMemberOfCollectivity(String memberId, String collectivityId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM collectivity_member WHERE member_id=? AND collectivity_id=?");
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Member save(Member member) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    INSERT INTO member (id,first_name,last_name,birth_date,gender,address,
                        profession,phone_number,email,occupation,adhesion_date,collectivity_id)
                    VALUES (?,?,?,?,?::gender_enum,?,?,?,?,?::occupation_enum,?,?)
                    """);
            ps.setString(1, member.getId());
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getLastName());
            ps.setDate(4, Date.valueOf(member.getBirthDate()));
            ps.setString(5, member.getGender().name());
            ps.setString(6, member.getAddress());
            ps.setString(7, member.getProfession());
            ps.setString(8, member.getPhoneNumber());
            ps.setString(9, member.getEmail());
            ps.setString(10, member.getOccupation().name());
            ps.setDate(11, Date.valueOf(member.getAdhesionDate()));
            ps.setString(12, member.getCollectivityId());
            ps.executeUpdate();
            return member;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void saveCollectivityMember(String collectivityId, String memberId, MemberOccupationEnum occupation) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO collectivity_member (collectivity_id, member_id, occupation) VALUES (?,?,?::occupation_enum) ON CONFLICT DO NOTHING");
            ps.setString(1, collectivityId);
            ps.setString(2, memberId);
            ps.setString(3, occupation.name());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void saveReferee(String memberId, String refereeId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO member_referees (member_id,referee_id) VALUES (?,?)");
            ps.setString(1, memberId);
            ps.setString(2, refereeId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateCollectivityId(String memberId, String collectivityId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE member SET collectivity_id=? WHERE id=?");
            ps.setString(1, collectivityId);
            ps.setString(2, memberId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateOccupation(String memberId, MemberOccupationEnum occupation) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE member SET occupation=?::occupation_enum WHERE id=?");
            ps.setString(1, occupation.name());
            ps.setString(2, memberId);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Member mapRow(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(rs.getString("id"));
        m.setFirstName(rs.getString("first_name"));
        m.setLastName(rs.getString("last_name"));
        m.setBirthDate(rs.getDate("birth_date").toLocalDate());
        m.setGender(GenderEnum.valueOf(rs.getString("gender")));
        m.setAddress(rs.getString("address"));
        m.setProfession(rs.getString("profession"));
        m.setPhoneNumber(rs.getString("phone_number"));
        m.setEmail(rs.getString("email"));
        m.setOccupation(MemberOccupationEnum.valueOf(rs.getString("occupation")));
        m.setAdhesionDate(rs.getDate("adhesion_date").toLocalDate());
        m.setCollectivityId(rs.getString("collectivity_id"));
        return m;
    }
}
