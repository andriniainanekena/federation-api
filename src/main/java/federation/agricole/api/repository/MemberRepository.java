package federation.agricole.api.repository;

import federation.agricole.api.entity.GenderEnum;
import federation.agricole.api.entity.Member;
import federation.agricole.api.entity.MemberOccupationEnum;
import federation.agricole.api.exception.NotFoundException;
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    SELECT id, first_name, last_name, birth_date, gender, address,
                           profession, phone_number, email, occupation, adhesion_date, collectivity_id
                    FROM member
                    WHERE id = ?
                    """);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRow(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findRefereesByMemberId(String memberId) {
        List<Member> referees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender, m.address,
                           m.profession, m.phone_number, m.email, m.occupation, m.adhesion_date, m.collectivity_id
                    FROM member m
                    JOIN member_referees mr ON m.id = mr.referee_id
                    WHERE mr.member_id = ?
                    """);
            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                referees.add(mapRow(resultSet));
            }
            return referees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Member save(Member member) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    INSERT INTO member (id, first_name, last_name, birth_date, gender, address,
                                       profession, phone_number, email, occupation, adhesion_date, collectivity_id)
                    VALUES (?, ?, ?, ?, ?::gender_enum, ?, ?, ?, ?, ?::occupation_enum, ?, ?)
                    """);
            preparedStatement.setString(1, member.getId());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setDate(4, Date.valueOf(member.getBirthDate()));
            preparedStatement.setString(5, member.getGender().name());
            preparedStatement.setString(6, member.getAddress());
            preparedStatement.setString(7, member.getProfession());
            preparedStatement.setString(8, member.getPhoneNumber());
            preparedStatement.setString(9, member.getEmail());
            preparedStatement.setString(10, member.getOccupation().name());
            preparedStatement.setDate(11, Date.valueOf(member.getAdhesionDate()));
            preparedStatement.setString(12, member.getCollectivityId());
            preparedStatement.executeUpdate();
            return member;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveReferee(String memberId, String refereeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    INSERT INTO member_referees (member_id, referee_id)
                    VALUES (?, ?)
                    """);
            preparedStatement.setString(1, memberId);
            preparedStatement.setString(2, refereeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCollectivityId(String memberId, String collectivityId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    UPDATE member SET collectivity_id = ? WHERE id = ?
                    """);
            preparedStatement.setString(1, collectivityId);
            preparedStatement.setString(2, memberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOccupation(String memberId, MemberOccupationEnum occupation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    UPDATE member SET occupation = ?::occupation_enum WHERE id = ?
                    """);
            preparedStatement.setString(1, occupation.name());
            preparedStatement.setString(2, memberId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member mapRow(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getString("id"));
        member.setFirstName(resultSet.getString("first_name"));
        member.setLastName(resultSet.getString("last_name"));
        member.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
        member.setGender(GenderEnum.valueOf(resultSet.getString("gender")));
        member.setAddress(resultSet.getString("address"));
        member.setProfession(resultSet.getString("profession"));
        member.setPhoneNumber(resultSet.getString("phone_number"));
        member.setEmail(resultSet.getString("email"));
        member.setOccupation(MemberOccupationEnum.valueOf(resultSet.getString("occupation")));
        member.setAdhesionDate(resultSet.getDate("adhesion_date").toLocalDate());
        member.setCollectivityId(resultSet.getString("collectivity_id"));
        return member;
    }
}
