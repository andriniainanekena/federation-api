package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
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

}

