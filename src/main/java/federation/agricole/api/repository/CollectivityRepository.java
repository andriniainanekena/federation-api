package federation.agricole.api.repository;


import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.entity.CollectivityStructure;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class CollectivityRepository {
    Connection connection;
    MemberRepository memberRepository;

    public CollectivityRepository(Connection connection, MemberRepository memberRepository) {
        this.connection = connection;
        this.memberRepository = memberRepository;
    }

    public Optional<Collectivity> findById(String id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    SELECT id, location, date_creation FROM collectivity WHERE id = ?
                    """);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(resultSet.getString("id"));
                collectivity.setLocation(resultSet.getString("location"));
                collectivity.setDateCreation(resultSet.getDate("date_creation").toLocalDate());
                return Optional.of(collectivity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity save(Collectivity collectivity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    INSERT INTO collectivity (id, location, date_creation)
                    VALUES (?, ?, ?)
                    """);
            preparedStatement.setString(1, collectivity.getId());
            preparedStatement.setString(2, collectivity.getLocation());
            preparedStatement.setDate(3, Date.valueOf(collectivity.getDateCreation()));
            preparedStatement.executeUpdate();
            return collectivity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveStructure(CollectivityStructure structure) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    INSERT INTO collectivity_structure
                        (collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id)
                    VALUES (?, ?, ?, ?, ?)
                    """);
            preparedStatement.setString(1, structure.getCollectivityId());
            preparedStatement.setString(2, structure.getPresident().getId());
            preparedStatement.setString(3, structure.getVicePresident().getId());
            preparedStatement.setString(4, structure.getTreasurer().getId());
            preparedStatement.setString(5, structure.getSecretary().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
