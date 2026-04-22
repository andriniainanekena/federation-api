package federation.agricole.api.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CollectivityStructureRest {
    private MemberRest president;
    private MemberRest vicePresident;
    private MemberRest treasurer;
    private MemberRest secretary;

    public CollectivityStructureRest(MemberRest president, MemberRest vicePresident,
                                     MemberRest treasurer, MemberRest secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

}
