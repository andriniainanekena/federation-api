package entity;

import federation.agricole.api.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class Collectivity {
    private String id;
    private String location;
    private LocalDate dateCreation;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {

    }

}
