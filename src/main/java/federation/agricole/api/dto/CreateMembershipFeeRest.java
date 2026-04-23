package federation.agricole.api.dto;

import federation.agricole.api.entity.FrequencyEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateMembershipFeeRest {
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private BigDecimal amount;
    private String label;

    public CreateMembershipFeeRest() {}

    public LocalDate getEligibleFrom() { return eligibleFrom; }
    public void setEligibleFrom(LocalDate eligibleFrom) { this.eligibleFrom = eligibleFrom; }

    public FrequencyEnum getFrequency() { return frequency; }
    public void setFrequency(FrequencyEnum frequency) { this.frequency = frequency; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}

