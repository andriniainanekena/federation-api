package federation.agricole.api.entity;

public class CollectivityStructure {
    private String collectivityId;
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;

    public CollectivityStructure() {
    }

    public CollectivityStructure(String collectivityId, Member president, Member vicePresident,
                                  Member treasurer, Member secretary) {
        this.collectivityId = collectivityId;
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public Member getPresident() { return president; }
    public void setPresident(Member president) { this.president = president; }

    public Member getVicePresident() { return vicePresident; }
    public void setVicePresident(Member vicePresident) { this.vicePresident = vicePresident; }

    public Member getTreasurer() { return treasurer; }
    public void setTreasurer(Member treasurer) { this.treasurer = treasurer; }

    public Member getSecretary() { return secretary; }
    public void setSecretary(Member secretary) { this.secretary = secretary; }
}
