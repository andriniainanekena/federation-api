package federation.agricole.api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MembershipFee {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private BigDecimal amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFee() {}

    public MembershipFee(String id, String collectivityId, LocalDate eligibleFrom, FrequencyEnum frequency, BigDecimal amount, String label, ActivityStatusEnum status) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

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

