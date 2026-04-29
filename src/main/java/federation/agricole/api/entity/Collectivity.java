package federation.agricole.api.entity;

import java.time.LocalDate;
import java.util.List;

public class Collectivity {
    private String id;
    private String name;
    private Integer number;
    private String location;
    private LocalDate dateCreation;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {}

    public Collectivity(String id, String location, LocalDate dateCreation,
                        CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.location = location;
        this.dateCreation = dateCreation;
        this.structure = structure;
        this.members = members;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public CollectivityStructure getStructure() { return structure; }
    public void setStructure(CollectivityStructure structure) { this.structure = structure; }
    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
}
