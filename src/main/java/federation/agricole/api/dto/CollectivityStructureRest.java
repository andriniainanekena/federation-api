package federation.agricole.api.dto;

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

    public MemberRest getPresident() { return president; }
    public MemberRest getVicePresident() { return vicePresident; }
    public MemberRest getTreasurer() { return treasurer; }
    public MemberRest getSecretary() { return secretary; }
}
