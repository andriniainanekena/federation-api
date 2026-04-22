package federation.agricole.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CollectivityRest {
    private String id;
    private String location;
    private CollectivityStructureRest structure;
    private List<MemberRest> members;

    public CollectivityRest(String id, String location,
                            CollectivityStructureRest structure, List<MemberRest> members) {
        this.id = id;
        this.location = location;
        this.structure = structure;
        this.members = members;
    }

}
