package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCollectivityStructureRest {
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;

    public CreateCollectivityStructureRest() {
    }
}
