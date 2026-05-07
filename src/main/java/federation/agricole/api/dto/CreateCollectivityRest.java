package federation.agricole.api.dto;

import java.util.List;

public class CreateCollectivityRest {
    private String location;
    private List<String> members;
    private boolean federationApproval;
    private CreateCollectivityStructureRest structure;

    public CreateCollectivityRest() {
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }

    public boolean isFederationApproval() { return federationApproval; }
    public void setFederationApproval(boolean federationApproval) { this.federationApproval = federationApproval; }

    public CreateCollectivityStructureRest getStructure() { return structure; }
    public void setStructure(CreateCollectivityStructureRest structure) { this.structure = structure; }
}
