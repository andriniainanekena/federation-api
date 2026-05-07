package federation.agricole.api.dto;

public class CollectivityOverallStatisticsRest {
    private CollectivityInformationRest collectivityInformation;
    private Integer newMembersNumber;
    private Double overallMemberCurrentDuePercentage;
    private Double overallMemberAssiduityPercentage;

    public CollectivityOverallStatisticsRest(CollectivityInformationRest collectivityInformation,
                                             Integer newMembersNumber,
                                             Double overallMemberCurrentDuePercentage,
                                             Double overallMemberAssiduityPercentage) {
        this.collectivityInformation = collectivityInformation;
        this.newMembersNumber = newMembersNumber;
        this.overallMemberCurrentDuePercentage = overallMemberCurrentDuePercentage;
        this.overallMemberAssiduityPercentage = overallMemberAssiduityPercentage;
    }

    public CollectivityInformationRest getCollectivityInformation() { return collectivityInformation; }
    public Integer getNewMembersNumber() { return newMembersNumber; }
    public Double getOverallMemberCurrentDuePercentage() { return overallMemberCurrentDuePercentage; }
    public Double getOverallMemberAssiduityPercentage() { return overallMemberAssiduityPercentage; }
}
