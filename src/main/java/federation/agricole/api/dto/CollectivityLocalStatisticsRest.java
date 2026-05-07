package federation.agricole.api.dto;

public class CollectivityLocalStatisticsRest {
    private MemberDescriptionRest memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;
    private Double assiduityPercentage;

    public CollectivityLocalStatisticsRest(MemberDescriptionRest memberDescription,
                                           Double earnedAmount, Double unpaidAmount,
                                           Double assiduityPercentage) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
        this.assiduityPercentage = assiduityPercentage;
    }

    public MemberDescriptionRest getMemberDescription() { return memberDescription; }
    public Double getEarnedAmount() { return earnedAmount; }
    public Double getUnpaidAmount() { return unpaidAmount; }
    public Double getAssiduityPercentage() { return assiduityPercentage; }
}
