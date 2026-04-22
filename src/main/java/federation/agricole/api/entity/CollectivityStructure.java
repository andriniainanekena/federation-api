package federation.agricole.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CollectivityStructure {
    private String collectivityId;
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;

    public CollectivityStructure() {

    }
    }
