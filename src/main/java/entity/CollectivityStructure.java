package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CollectivityStructure {
    private String collectivityId;
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;

    public CollectivityStructure() {

    }
}

