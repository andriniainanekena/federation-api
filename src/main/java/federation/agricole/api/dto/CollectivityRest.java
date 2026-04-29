package federation.agricole.api.dto;

import java.util.List;

public class CollectivityRest {
    private String id;
    private String name;
    private Integer number;
    private String location;
    private CollectivityStructureRest structure;
    private List<MemberRest> members;

    public CollectivityRest(String id, String name, Integer number, String location,
                            CollectivityStructureRest structure, List<MemberRest> members) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.location = location;
        this.structure = structure;
        this.members = members;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Integer getNumber() { return number; }
    public String getLocation() { return location; }
    public CollectivityStructureRest getStructure() { return structure; }
    public List<MemberRest> getMembers() { return members; }
}
