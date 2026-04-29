package federation.agricole.api.repository;

import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.entity.CollectivityStructure;
import federation.agricole.api.entity.Member;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
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
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id,name,number,location,specialty,date_creation FROM collectivity WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<Collectivity> findByIdFull(String id) {
        Optional<Collectivity> opt = findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Collectivity c = opt.get();

        // Chargement des membres via junction table
        List<Member> members = memberRepository.findAllByCollectivityId(id);
        c.setMembers(members);

        // Chargement du bureau
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT president_id,vice_president_id,treasurer_id,secretary_id FROM collectivity_structure WHERE collectivity_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CollectivityStructure structure = new CollectivityStructure();
                structure.setCollectivityId(id);
                memberRepository.findById(rs.getString("president_id")).ifPresent(structure::setPresident);
                memberRepository.findById(rs.getString("vice_president_id")).ifPresent(structure::setVicePresident);
                memberRepository.findById(rs.getString("treasurer_id")).ifPresent(structure::setTreasurer);
                memberRepository.findById(rs.getString("secretary_id")).ifPresent(structure::setSecretary);
                c.setStructure(structure);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }

        return Optional.of(c);
    }

    public boolean existsByNameAndIdNot(String name, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM collectivity WHERE name=? AND id!=?");
            ps.setString(1, name); ps.setString(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean existsByNumberAndIdNot(Integer number, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM collectivity WHERE number=? AND id!=?");
            ps.setInt(1, number); ps.setString(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Collectivity save(Collectivity collectivity) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO collectivity (id,location,date_creation) VALUES (?,?,?)");
            ps.setString(1, collectivity.getId());
            ps.setString(2, collectivity.getLocation());
            ps.setDate(3, Date.valueOf(collectivity.getDateCreation()));
            ps.executeUpdate();
            return collectivity;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Collectivity updateInformation(String id, String name, Integer number) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE collectivity SET name=?, number=? WHERE id=?");
            ps.setString(1, name); ps.setInt(2, number); ps.setString(3, id);
            ps.executeUpdate();
            return findById(id).orElseThrow();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void saveStructure(CollectivityStructure structure) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO collectivity_structure (collectivity_id,president_id,vice_president_id,treasurer_id,secretary_id) VALUES (?,?,?,?,?)");
            ps.setString(1, structure.getCollectivityId());
            ps.setString(2, structure.getPresident().getId());
            ps.setString(3, structure.getVicePresident().getId());
            ps.setString(4, structure.getTreasurer().getId());
            ps.setString(5, structure.getSecretary().getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Collectivity mapRow(ResultSet rs) throws SQLException {
        Collectivity c = new Collectivity();
        c.setId(rs.getString("id"));
        c.setName(rs.getString("name"));
        Object number = rs.getObject("number");
        if (number != null) c.setNumber((Integer) number);
        c.setLocation(rs.getString("location"));
        c.setDateCreation(rs.getDate("date_creation").toLocalDate());
        return c;
    }
}
