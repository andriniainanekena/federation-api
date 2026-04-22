package federation.agricole.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCollectivityStructureRest {
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;

    public CreateCollectivityStructureRest() {
    }

}
