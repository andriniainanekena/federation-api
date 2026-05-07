package federation.agricole.api.dto;

import federation.agricole.api.entity.ActivityStatusEnum;
import federation.agricole.api.entity.FrequencyEnum;

import java.time.LocalDate;

public class MembershipFeeRest {
    private String id;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private Double amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFeeRest(String id, LocalDate eligibleFrom, FrequencyEnum frequency,
                             Double amount, String label, ActivityStatusEnum status) {
        this.id = id;
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
        this.status = status;
    }

    public String getId() { return id; }
    public LocalDate getEligibleFrom() { return eligibleFrom; }
    public FrequencyEnum getFrequency() { return frequency; }
    public Double getAmount() { return amount; }
    public String getLabel() { return label; }
    public ActivityStatusEnum getStatus() { return status; }
}
