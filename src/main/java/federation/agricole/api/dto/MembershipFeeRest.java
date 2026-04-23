package federation.agricole.api.dto;

import federation.agricole.api.entity.ActivityStatusEnum;
import federation.agricole.api.entity.FrequencyEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MembershipFeeRest {
    private String id;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private BigDecimal amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFeeRest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getEligibleFrom() { return eligibleFrom; }
    public void setEligibleFrom(LocalDate eligibleFrom) { this.eligibleFrom = eligibleFrom; }

    public FrequencyEnum getFrequency() { return frequency; }
    public void setFrequency(FrequencyEnum frequency) { this.frequency = frequency; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public ActivityStatusEnum getStatus() { return status; }
    public void setStatus(ActivityStatusEnum status) { this.status = status; }
}

